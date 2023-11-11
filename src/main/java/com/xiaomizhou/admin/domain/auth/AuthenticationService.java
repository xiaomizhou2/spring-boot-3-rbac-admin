package com.xiaomizhou.admin.domain.auth;

import com.xiaomizhou.admin.domain.auth.jwt.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/7
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationRepository repository;
    private final AuthenticationUserRepository userRepository;

    public AuthenticationResponse authenticate(String username) {
        String accessToken = jwtService.generateToken(username);
        String refreshToken = jwtService.generateRefreshToken(username);

        int count = repository.countAllByPrincipalNameAndDeleted(username, false);
        if (count > 0) {
            repository.deleteByPrincipalName(username);
        }

        saveAuthenticationToken(username, accessToken, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean isTokenValid(String token) {
        String username = jwtService.extractClaim(token, Claims::getSubject);
        AuthenticationUser user = userRepository.findByUsername(username);
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);
        return user != null && expiration.before(new Date());
    }

    private void saveAuthenticationToken(String username, String accessToken, String refreshToken) {
        Date accessTokenIssued = jwtService.extractClaim(accessToken, Claims::getIssuedAt);
        Date accessTokenExpires = jwtService.extractClaim(accessToken, Claims::getExpiration);

        Date refreshTokenIssued = jwtService.extractClaim(refreshToken, Claims::getIssuedAt);
        Date refreshTokenExpires = jwtService.extractClaim(refreshToken, Claims::getExpiration);

        AuthenticationEntity authentication = AuthenticationEntity.builder()
                .accessToken(accessToken)
                .accessTokenIssued(accessTokenIssued)
                .accessTokenExpires(accessTokenExpires)
                .principalName(username)
                .refreshToken(refreshToken)
                .refreshTokenIssued(refreshTokenIssued)
                .refreshTokenExpires(refreshTokenExpires)
                .deleted(false)
                .build();

        repository.save(authentication);
    }

}
