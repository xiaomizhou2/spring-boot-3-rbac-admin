package com.xiaomizhou.admin.domain.auth;

import com.xiaomizhou.admin.domain.user.UserEntity;
import com.xiaomizhou.admin.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/9
 */
@Component
@RequiredArgsConstructor
public class AuthenticationUserRepository {
    private final UserRepository repository;

    public AuthenticationUser findByUsername(String username) {
        UserEntity user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        AuthenticationUser authenticationUser = new AuthenticationUser();
        BeanUtils.copyProperties(user, authenticationUser);
        return authenticationUser;
    }
}
