package com.xiaomizhou.admin.domain.auth.authorization;

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
public class AuthorizationUserRepository {
    private final UserRepository repository;


    public AuthorizationUserInfo findByUsername(String username) {
        UserEntity user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        AuthorizationUserInfo authorizationUserInfo = new AuthorizationUserInfo();
        BeanUtils.copyProperties(user, authorizationUserInfo);
        return authorizationUserInfo;
    }
}
