package com.xiaomizhou.admin.domain.auth.handler;

import com.xiaomizhou.admin.domain.auth.AuthenticationResponse;
import com.xiaomizhou.admin.domain.auth.jwt.JwtAuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/7
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessAuthenticationHandler implements AuthenticationSuccessHandler {
    private final JwtAuthenticationService jwtAuthenticationService;
    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        AuthenticationResponse authenticationResponse = jwtAuthenticationService.issued(username);
        log.info("登录成功，返回令牌信息:{}", authenticationResponse);
        if (authenticationResponse != null) {
            //将令牌信息返回
            ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
            httpResponse.setStatusCode(HttpStatus.OK);
            mappingJackson2HttpMessageConverter.write(authenticationResponse, MediaType.APPLICATION_JSON, httpResponse);
        }
    }
}
