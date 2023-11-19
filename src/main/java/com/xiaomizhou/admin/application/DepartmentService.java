package com.xiaomizhou.admin.application;

import com.xiaomizhou.admin.domain.department.DepartmentEntity;
import com.xiaomizhou.admin.domain.department.DepartmentRepository;
import com.xiaomizhou.admin.interfaces.vo.DepartmentQueryVo;
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
public class DepartmentService {

    private final DepartmentRepository repository;

    public Page<DepartmentEntity> list(DepartmentQueryVo query, Pageable pageable) {
        DepartmentEntity department = DepartmentEntity.builder()
                .code(query.getCode())
                .name(query.getName())
                .build();

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("code", ExampleMatcher.GenericPropertyMatcher::startsWith)
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::startsWith);

        Example<DepartmentEntity> example = Example.of(department, exampleMatcher);
        return repository.findAll(example, pageable);
    }

    public DepartmentEntity findById(Integer id) {
        return repository.findById(id).get();
    }

    public void save(DepartmentEntity departmentEntity) {
        repository.save(departmentEntity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
