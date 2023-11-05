package com.xiaomizhou.admin.user;

import com.xiaomizhou.admin.role.Role;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<Page<UserResponse>> list(UserRequest query, @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(service.page(UserConverter.INSTANCES.converterReq2Entity(query), pageable)
                .map(UserConverter.INSTANCES::convertEntity2Resp));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(UserConverter.INSTANCES.convertEntity2Resp(service.findById(id)));
    }

    @PostMapping("/updateRole")
    public ResponseEntity<?> updateRoles(@RequestBody ChangeRoleRequest request) {
        User user = UserConverter.INSTANCES.converterReq2Entity(request);
        service.update(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/lock/{id}")
    public ResponseEntity<?> lock(@PathVariable("id") Integer id) {
        service.lock(id);
        return ResponseEntity.ok().build();
    }

}
