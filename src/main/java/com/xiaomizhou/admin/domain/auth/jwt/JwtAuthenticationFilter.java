package com.xiaomizhou.admin.domain.auth.jwt;

import com.xiaomizhou.admin.domain.auth.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String SPRING_SECURITY_AUTH_HEADER_KEY = "Authorization";
    public static final String SPRING_SECURITY_AUTH_PREFIX_KEY = "Bearer";
    private final RequestMatcher requiresAuthenticationRequestMatcher = new AntPathRequestMatcher("/auth/authentication", "GET");
    private final AuthenticationService authenticationService;
    private String usernameParameter = "username";
    private String passwordParameter = "password";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
}
