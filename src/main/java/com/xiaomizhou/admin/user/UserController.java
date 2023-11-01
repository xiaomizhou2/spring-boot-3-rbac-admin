package com.xiaomizhou.admin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> list(Map<String, Object> query, Pageable pageable) {
        return ResponseEntity.ok(service.page(query, pageable).map(UserConverter.INSTANCES::convertEntity2Resp));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(UserConverter.INSTANCES.convertEntity2Resp(service.findById(id)));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody UserRequest request) {
        service.update(id, request);
        return ResponseEntity.ok().build();
    }

}
