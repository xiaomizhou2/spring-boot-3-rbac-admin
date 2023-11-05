package com.xiaomizhou.admin.dept;

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

    public Page<Department> list(Department department, Pageable pageable) {
        Example<Department> example = Example.of(department);
        return repository.findAll(example, pageable);
    }

    public Department findById(Integer id) {
        return repository.findById(id).get();
    }

    public void save(Department department) {
        repository.save(department);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
