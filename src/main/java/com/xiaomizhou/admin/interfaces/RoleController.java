package com.xiaomizhou.admin.interfaces;

import com.xiaomizhou.admin.domain.role.RoleEntity;
import com.xiaomizhou.admin.application.RoleService;
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
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<Page<RoleEntity>> list(RoleEntity roleEntity, Pageable pageable) {
        return ResponseEntity.ok(service.list(roleEntity, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleEntity> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RoleEntity roleEntity) {
        service.create(roleEntity);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody RoleEntity roleEntity) {
        service.update(roleEntity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
