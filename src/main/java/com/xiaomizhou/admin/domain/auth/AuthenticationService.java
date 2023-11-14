package com.xiaomizhou.admin.domain.auth;

import com.xiaomizhou.admin.domain.auth.jwt.JwtService;
import com.xiaomizhou.admin.domain.auth.token.TokenEntity;
import com.xiaomizhou.admin.domain.auth.token.TokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 认证服务
 *
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/7
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final JwtService jwtService;
    private final TokenRepository repository;

    /**
     * 根据用户名签发token
     *
     * @param username
     * @return 认证Response
     */
    public AuthenticationResponse authenticate(String username) {
        String accessToken = jwtService.generateToken(username);
        String refreshToken = jwtService.generateRefreshToken(username);
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
        Date expiration = jwtService.extractClaim(accessToken, Claims::getExpiration);
        return tokenEntity != null && expiration.after(new Date());
    }

    private void saveAuthenticationToken(String username, String accessToken, String refreshToken) {
        Date accessTokenIssued = jwtService.extractClaim(accessToken, Claims::getIssuedAt);
        Date accessTokenExpires = jwtService.extractClaim(accessToken, Claims::getExpiration);
        Date refreshTokenIssued = jwtService.extractClaim(refreshToken, Claims::getIssuedAt);
        Date refreshTokenExpires = jwtService.extractClaim(refreshToken, Claims::getExpiration);
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
}
