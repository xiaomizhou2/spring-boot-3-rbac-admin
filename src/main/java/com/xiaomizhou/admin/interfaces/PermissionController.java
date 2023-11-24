package com.xiaomizhou.admin.interfaces;

import com.xiaomizhou.admin.domain.permission.PermissionEntity;
import com.xiaomizhou.admin.application.PermissionService;
import com.xiaomizhou.admin.domain.permission.validation.NotConflictPermission;
import com.xiaomizhou.admin.domain.permission.validation.UniquePermission;
import com.xiaomizhou.admin.interfaces.vo.ApiQueryVo;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/4
 */
@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService service;

    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<Page<PermissionEntity>> list(ApiQueryVo query,
                                                       @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(service.list(query, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionEntity> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<?> create(@Valid @UniquePermission @RequestBody PermissionEntity permissionEntity) {
        service.save(permissionEntity);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @NotConflictPermission @RequestBody PermissionEntity permissionEntity) {
        service.save(permissionEntity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
