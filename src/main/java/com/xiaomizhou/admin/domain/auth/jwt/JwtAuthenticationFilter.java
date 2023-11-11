package com.xiaomizhou.admin.domain.auth.jwt;

import com.xiaomizhou.admin.domain.auth.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/8
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String SPRING_SECURITY_AUTH_HEADER_KEY = "Authorization";
    public static final String SPRING_SECURITY_AUTH_PREFIX_KEY = "Bearer ";
    private final AuthenticationService authenticationService;
    private String usernameParameter = "username";
    private String passwordParameter = "password";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(SPRING_SECURITY_AUTH_HEADER_KEY);
        if (token == null || !token.startsWith(SPRING_SECURITY_AUTH_PREFIX_KEY)) {
            chain.doFilter(request, response);
            return;
        }

        token = token.substring(SPRING_SECURITY_AUTH_PREFIX_KEY.length());
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("准备放入用户信息");
        }
    }
}
