package com.xiaomizhou.admin.interfaces;

import com.xiaomizhou.admin.domain.user.UserEntity;
import com.xiaomizhou.admin.application.UserService;
import com.xiaomizhou.admin.domain.user.validation.NotConflictUser;
import com.xiaomizhou.admin.domain.user.validation.UniqueUser;
import com.xiaomizhou.admin.interfaces.vo.UserQueryVo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "用户管理")
public class UserController {

    private final UserService service;

    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<Page<UserEntity>> list(UserQueryVo query,
                                                 @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(service.page(query, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<?> create(@Valid @UniqueUser @RequestBody UserEntity user) {
        service.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @NotConflictUser @RequestBody UserEntity user) {
        service.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
