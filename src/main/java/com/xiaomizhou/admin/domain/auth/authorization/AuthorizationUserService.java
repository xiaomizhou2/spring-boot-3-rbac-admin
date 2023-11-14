package com.xiaomizhou.admin.domain.auth.authorization;

import com.xiaomizhou.admin.domain.auth.jwt.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 授权服务
 *
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/9
 */
@RequiredArgsConstructor
public class AuthorizationUserService implements UserDetailsService {
    private final AuthorizationUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }
}
