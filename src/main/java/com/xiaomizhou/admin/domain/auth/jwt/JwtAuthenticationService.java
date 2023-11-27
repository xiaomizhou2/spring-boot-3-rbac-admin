package com.xiaomizhou.admin.domain.auth.jwt;

import com.xiaomizhou.admin.domain.auth.AuthenticationResponse;
import com.xiaomizhou.admin.domain.auth.token.TokenEntity;
import com.xiaomizhou.admin.domain.auth.token.TokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/15
 */
@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {
    private final JwtRepository jwtRepository;
    private final TokenRepository repository;

    /**
     * 根据用户名签发token
     *
     * @param username
     * @return 认证Response
     */
    public AuthenticationResponse issued(String username) {
        String accessToken = jwtRepository.generateToken(username);
        String refreshToken = jwtRepository.generateRefreshToken(username);
        saveAuthenticationToken(username, accessToken, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * 证实token有效
     *
     * @param accessToken
     * @return token是否有效
     */
    public boolean authenticateToken(String accessToken) {
        TokenEntity tokenEntity = repository.findAuthenticationEntityByAccessToken(accessToken);
        Date expiration = jwtRepository.extractClaim(accessToken, Claims::getExpiration);
        return tokenEntity != null && expiration.after(new Date());
    }

    /**
     * 刷新token
     *
     * @param username
     * @param refreshToken
     * @return 刷新token
     */
    public AuthenticationResponse refreshToken(String username, String refreshToken) {
        TokenEntity entity = repository.findTokenEntityByRefreshToken(refreshToken);
        String token = jwtRepository.generateToken(username);
        this.updateAccessToken(token, entity);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * 提取相应的内容
     *
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return 内容
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return jwtRepository.extractClaim(token, claimsResolver);
    }

    private void saveAuthenticationToken(String username, String accessToken, String refreshToken) {
        Date accessTokenIssued = jwtRepository.extractClaim(accessToken, Claims::getIssuedAt);
        Date accessTokenExpires = jwtRepository.extractClaim(accessToken, Claims::getExpiration);
        Date refreshTokenIssued = jwtRepository.extractClaim(refreshToken, Claims::getIssuedAt);
        Date refreshTokenExpires = jwtRepository.extractClaim(refreshToken, Claims::getExpiration);
        TokenEntity authentication = TokenEntity.builder()
                .accessToken(accessToken)
                .accessTokenIssued(accessTokenIssued)
                .accessTokenExpires(accessTokenExpires)
                .principalName(username)
                .refreshToken(refreshToken)
                .refreshTokenIssued(refreshTokenIssued)
                .refreshTokenExpires(refreshTokenExpires)
                .build();
        repository.save(authentication);
    }

    private void updateAccessToken(String accessToken, TokenEntity entity) {
        Date accessTokenIssued = jwtRepository.extractClaim(accessToken, Claims::getIssuedAt);
        Date accessTokenExpires = jwtRepository.extractClaim(accessToken, Claims::getExpiration);
        entity.setAccessTokenIssued(accessTokenIssued);
        entity.setAccessTokenExpires(accessTokenExpires);
        repository.save(entity);
    }

}
