package com.xiaomizhou.admin.role;

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

    public Page<Role> list(Role role, Pageable pageable) {
        Example<Role> example = Example.of(role);
        return repository.findAll(example, pageable);
    }

    public Role findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("数据不存在"));
    }

    public void create(Role role) {
        repository.save(role);
    }

    public void update(Role role) {
        repository.save(role);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

}
