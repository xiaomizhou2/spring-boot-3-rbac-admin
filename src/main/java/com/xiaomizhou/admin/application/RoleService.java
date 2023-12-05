package com.xiaomizhou.admin.application;

import com.xiaomizhou.admin.domain.role.RoleEntity;
import com.xiaomizhou.admin.domain.role.RoleRepository;
import com.xiaomizhou.admin.interfaces.vo.RoleQueryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/4
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Page<RoleEntity> list(RoleQueryVo query, Pageable pageable) {
        RoleEntity role = RoleEntity.builder()
                .code(query.getCode())
                .name(query.getName())
                .build();

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("code", ExampleMatcher.GenericPropertyMatcher::startsWith)
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::startsWith);

        Example<RoleEntity> example = Example.of(role, exampleMatcher);
        return repository.findAll(example, pageable);
    }

    public RoleEntity findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("数据不存在"));
    }

    public void save(RoleEntity roleEntity) {
        repository.save(roleEntity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

}
