package com.xiaomizhou.admin.application;

import com.xiaomizhou.admin.domain.role.RoleEntity;
import com.xiaomizhou.admin.domain.role.RoleRepository;
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
public class RoleService {

    private final RoleRepository repository;

    public Page<RoleEntity> list(RoleEntity roleEntity, Pageable pageable) {
        Example<RoleEntity> example = Example.of(roleEntity);
        return repository.findAll(example, pageable);
    }

    public RoleEntity findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("数据不存在"));
    }

    public void create(RoleEntity roleEntity) {
        repository.save(roleEntity);
    }

    public void update(RoleEntity roleEntity) {
        repository.save(roleEntity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

}
