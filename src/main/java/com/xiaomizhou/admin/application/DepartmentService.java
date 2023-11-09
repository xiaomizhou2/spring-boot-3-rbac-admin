package com.xiaomizhou.admin.application;

import com.xiaomizhou.admin.domain.department.DepartmentEntity;
import com.xiaomizhou.admin.domain.department.DepartmentRepository;
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
public class DepartmentService {

    private final DepartmentRepository repository;

    public Page<DepartmentEntity> list(DepartmentEntity departmentEntity, Pageable pageable) {
        Example<DepartmentEntity> example = Example.of(departmentEntity);
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
