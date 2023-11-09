package com.xiaomizhou.admin.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/9
 */
@RequiredArgsConstructor
public class AuthenticationUserDetailService implements UserDetailsService {

    private final AuthenticationUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }
}
