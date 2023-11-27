package com.xiaomizhou.admin.domain.auth.token;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/11
 */
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

    void deleteByPrincipalName(String principalName);

    int countAllByPrincipalName(String principalName);

    TokenEntity findAuthenticationEntityByAccessToken(String accessToken);

    TokenEntity findTokenEntityByRefreshToken(String refreshToken);
}
