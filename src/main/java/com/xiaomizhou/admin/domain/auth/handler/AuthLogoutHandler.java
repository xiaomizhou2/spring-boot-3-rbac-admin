package com.xiaomizhou.admin.domain.auth.handler;

import com.xiaomizhou.admin.domain.auth.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/24
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            String principal = authentication.getName();
            tokenRepository.deleteByPrincipalName(principal);
        } catch (Exception e) {
            log.error("退出用户出现问题:{}", e);
            throw new RuntimeException("退出出现问题");
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}
