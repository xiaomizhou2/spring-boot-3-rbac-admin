package com.xiaomizhou.admin.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/4
 */
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository repository;

    public Page<Permission> list(Permission permission, Pageable pageable) {
        Example<Permission> example = Example.of(permission);
        return repository.findAll(example, pageable);
    }

    public Permission findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("数据不存在"));
    }

    public void save(Permission permission) {
        repository.save(permission);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
