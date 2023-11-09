package com.xiaomizhou.admin.domain.auth;

import com.xiaomizhou.admin.domain.user.UserEntity;
import com.xiaomizhou.admin.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/7
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationResponse authenticate(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        return AuthenticationResponse.builder()
                .accessToken("123456").refreshToken("789").build();
    }

}
