package com.xiaomizhou.admin.auth;

import com.xiaomizhou.admin.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/7
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationHandler implements AuthenticationSuccessHandler {
    private final AuthenticationService authenticationService;
    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserEntity user) {
            String username = user.getUsername();
            AuthenticationResponse authenticationResponse = authenticationService.authenticate(username);
            log.info("登录成功，返回令牌信息:{}", authenticationResponse);
            if (authenticationResponse != null) {
                //将令牌信息返回
                ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
                httpResponse.setStatusCode(HttpStatus.OK);
                mappingJackson2HttpMessageConverter.write(authenticationResponse, MediaType.APPLICATION_JSON, httpResponse);
            }
        }
    }
}
