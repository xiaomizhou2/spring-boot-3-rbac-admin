package com.xiaomizhou.admin.domain.auth.jwt;

import com.xiaomizhou.admin.domain.auth.AuthenticationDetailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/8
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String SPRING_SECURITY_AUTH_HEADER_KEY = "Authorization";
    private static final String SPRING_SECURITY_AUTH_PREFIX_KEY = "Bearer ";
    private final JwtAuthenticationService jwtAuthenticationService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(SPRING_SECURITY_AUTH_HEADER_KEY);
        if (token == null || !token.startsWith(SPRING_SECURITY_AUTH_PREFIX_KEY)) {
            chain.doFilter(request, response);
            return;
        }
        token = token.substring(SPRING_SECURITY_AUTH_PREFIX_KEY.length());
        if (SecurityContextHolder.getContext().getAuthentication() == null && jwtAuthenticationService.authenticateToken(token)) {
            //加载用户权限信息
            String username = jwtAuthenticationService.extractClaim(token, Claims::getSubject);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }
}
