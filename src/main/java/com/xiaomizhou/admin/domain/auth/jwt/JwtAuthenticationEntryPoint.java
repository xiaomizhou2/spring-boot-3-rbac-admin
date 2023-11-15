package com.xiaomizhou.admin.domain.auth.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * jwt认证失败处理端点
 *
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/15
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error("认证失败，失败原因:{}", e.getMessage());
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        mappingJackson2HttpMessageConverter.write(e.getMessage(), MediaType.APPLICATION_JSON, httpResponse);
    }
}
