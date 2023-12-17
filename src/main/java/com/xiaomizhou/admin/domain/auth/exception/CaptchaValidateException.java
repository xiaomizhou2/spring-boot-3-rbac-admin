package com.xiaomizhou.admin.domain.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/12/17
 */
public class CaptchaValidateException extends AuthenticationException {
    public CaptchaValidateException(String message) {
        super(message);
    }
}
