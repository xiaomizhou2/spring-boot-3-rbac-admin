package com.xiaomizhou.admin.domain.auth;

import com.xiaomizhou.admin.domain.auth.exception.CaptchaValidateException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/12/17
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaptchaValidateFilter extends OncePerRequestFilter {

    private final RequestMatcher requiresAuthenticationRequestMatcher = new AntPathRequestMatcher("/login", "POST");

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    private final String CAPTCHA_CODE_KEY = "captcha";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = request.getSession();
        try {
            String captchaCode = request.getParameter(CAPTCHA_CODE_KEY);
            this.validate(session, captchaCode);
        } catch (CaptchaValidateException e) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        } finally {
            session.removeAttribute(session.getId());
        }
        chain.doFilter(request, response);
    }

    private void validate(HttpSession session, String captchaCode) throws CaptchaValidateException{
        String codeInSession = (String) session.getAttribute(session.getId());
        if (StringUtils.isBlank(captchaCode)) {
            throw new CaptchaValidateException("验证码不能为空！");
        }
        if (codeInSession == null) {
            throw new CaptchaValidateException("验证码不存在！");
        }
        if (!captchaCode.equalsIgnoreCase(codeInSession)) {
            throw new CaptchaValidateException("验证码不正确！");
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
