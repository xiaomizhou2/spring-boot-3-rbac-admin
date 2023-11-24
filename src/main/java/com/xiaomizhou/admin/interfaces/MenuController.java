package com.xiaomizhou.admin.interfaces;

import com.xiaomizhou.admin.domain.menu.MenuEntity;
import com.xiaomizhou.admin.application.MenuService;
import com.xiaomizhou.admin.domain.menu.validation.NotConflictMenu;
import com.xiaomizhou.admin.domain.menu.validation.UniqueMenu;
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
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService service;

    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<Page<MenuEntity>> list(MenuEntity menuEntity,
                                                 @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(service.list(menuEntity, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuEntity> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<?> create(@Valid @UniqueMenu @RequestBody MenuEntity menuEntity) {
        service.save(menuEntity);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @NotConflictMenu @RequestBody MenuEntity menuEntity) {
        service.save(menuEntity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
