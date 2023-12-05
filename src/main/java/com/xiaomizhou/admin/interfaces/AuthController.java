package com.xiaomizhou.admin.interfaces;

import com.xiaomizhou.admin.domain.auth.UserInfo;
import com.xiaomizhou.admin.infrastructure.utils.SpringSecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/12/5
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/userInfo")
    public ResponseEntity<UserInfo> userInfo() {
        UserInfo userInfo = SpringSecurityUtils.currentUserInfo();
        return ResponseEntity.ok(userInfo);
    }
}
