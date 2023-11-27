package com.xiaomizhou.admin.infrastructure.configuration;

import com.xiaomizhou.admin.domain.auth.RefreshTokenFilter;
import com.xiaomizhou.admin.domain.auth.handler.AuthLogoutHandler;
import com.xiaomizhou.admin.domain.auth.handler.LoginFailureAuthenticationHandler;
import com.xiaomizhou.admin.domain.auth.jwt.JwtAuthenticationFilter;
import com.xiaomizhou.admin.domain.auth.handler.LoginSuccessAuthenticationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/5
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_URL_LIST = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/error"
    };

    private final AuthenticationProvider authenticationProvider;
    private final LoginSuccessAuthenticationHandler loginSuccessAuthenticationHandler;
    private final LoginFailureAuthenticationHandler loginFailureAuthenticationHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthLogoutHandler authLogoutHandler;
    private final RefreshTokenFilter refreshTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_URL_LIST).permitAll()
                                .anyRequest().authenticated())
                //设置session永远不会创建
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                // 增加jwt权限过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(refreshTokenFilter, JwtAuthenticationFilter.class)
                // 通过表单登录，增加登录成功处理器、登录失败处理器
                .formLogin(login -> login.successHandler(loginSuccessAuthenticationHandler).failureHandler(loginFailureAuthenticationHandler))
                // 退出登录
                .logout(logout -> logout.logoutUrl("/logout").addLogoutHandler(authLogoutHandler));

        return http.build();
    }

}
