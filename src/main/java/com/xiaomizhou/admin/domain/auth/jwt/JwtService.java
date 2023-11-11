package com.xiaomizhou.admin.domain.auth.jwt;

import com.xiaomizhou.admin.domain.auth.AuthenticationUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;

    public String generateToken(String username) {
        return this.generateToken(username, new HashMap<>());
    }

    public String generateToken(String username, Map<String, Object> claims) {
        return this.buildToken(claims, username, jwtProperties.getExpiration());
    }

    public String generateRefreshToken(String username) {
        return this.buildToken(new HashMap<>(), username, jwtProperties.getRefreshExpiration());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(generateSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 构建token
     *
     * @param claims
     * @param subject
     * @param expiration
     * @return token
     */
    private String buildToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(generateSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 生成签名key
     *
     * @return key
     */
    private Key generateSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
