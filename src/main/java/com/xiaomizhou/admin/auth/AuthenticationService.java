package com.xiaomizhou.admin.auth;

import com.xiaomizhou.admin.entity.UserEntity;
import com.xiaomizhou.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
