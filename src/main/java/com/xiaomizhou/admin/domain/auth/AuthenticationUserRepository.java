package com.xiaomizhou.admin.domain.auth;

import com.xiaomizhou.admin.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
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
        return new AuthenticationUser(repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在")));
    }
}
