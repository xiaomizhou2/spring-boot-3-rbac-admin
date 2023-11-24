package com.xiaomizhou.admin.application;

import com.xiaomizhou.admin.domain.permission.PermissionEntity;
import com.xiaomizhou.admin.domain.permission.PermissionRepository;
import com.xiaomizhou.admin.interfaces.vo.ApiQueryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public Page<PermissionEntity> list(ApiQueryVo query, Pageable pageable) {
        PermissionEntity api = PermissionEntity.builder()
                .url(query.getUrl())
                .method(query.getMethod())
                .build();

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("url", ExampleMatcher.GenericPropertyMatcher::startsWith);

        Example<PermissionEntity> example = Example.of(api, exampleMatcher);
        return repository.findAll(example, pageable);
    }

    public PermissionEntity findById(Integer id) {
        return repository.findById(id).get();
    }

    public void save(PermissionEntity permissionEntity) {
        repository.save(permissionEntity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
