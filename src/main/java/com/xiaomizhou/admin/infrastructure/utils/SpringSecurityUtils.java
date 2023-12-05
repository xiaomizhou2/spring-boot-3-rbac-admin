package com.xiaomizhou.admin.infrastructure.utils;

import com.xiaomizhou.admin.domain.auth.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/12/5
 */
public final class SpringSecurityUtils {

    private SpringSecurityUtils() {

    }

    public static UserInfo currentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserInfo userInfo) {
            return userInfo;
        }
        return null;
    }
}
