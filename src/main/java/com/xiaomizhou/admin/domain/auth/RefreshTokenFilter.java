package com.xiaomizhou.admin.domain.auth;

import com.xiaomizhou.admin.domain.auth.jwt.JwtAuthenticationService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 刷新token过滤器,通过此过滤器更新token
 *
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/27
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {
    private final RequestMatcher requiresAuthenticationRequestMatcher = new AntPathRequestMatcher("/refreshToken", "POST");
    private static final String SPRING_SECURITY_AUTH_PREFIX_KEY = "Bearer ";
    private final JwtAuthenticationService jwtAuthenticationService;
    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    private final HttpMessageConverter<String> stringrHttpResponseConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (!requiresAuthentication(request, response)) {
                chain.doFilter(request, response);
                return;
            }

            String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (refreshToken == null || !refreshToken.startsWith(SPRING_SECURITY_AUTH_PREFIX_KEY)) {
                chain.doFilter(request, response);
                return;
            }

            refreshToken = refreshToken.substring(SPRING_SECURITY_AUTH_PREFIX_KEY.length());
            String username = jwtAuthenticationService.extractClaim(refreshToken, Claims::getSubject);
            if (username != null) {
                AuthenticationResponse authenticationResponse = jwtAuthenticationService.refreshToken(username, refreshToken);
                ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
                httpResponse.setStatusCode(HttpStatus.OK);
                mappingJackson2HttpMessageConverter.write(authenticationResponse, MediaType.APPLICATION_JSON, httpResponse);
            }
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
            httpResponse.setStatusCode(HttpStatus.BAD_REQUEST);
            this.stringrHttpResponseConverter.write(exceptionMessage, null, httpResponse);
        }
    }

    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (this.requiresAuthenticationRequestMatcher.matches(request)) {
            return true;
        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(LogMessage.format("Did not match request to %s", this.requiresAuthenticationRequestMatcher));
        }
        return false;
    }
}
