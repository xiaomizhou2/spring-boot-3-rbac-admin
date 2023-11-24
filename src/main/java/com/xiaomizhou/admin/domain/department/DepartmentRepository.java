package com.xiaomizhou.admin.domain.department;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xiaomizhou
 * @date 2023/10/27
 * @email 521jx123@gmail.com
 */
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {

    boolean existsByCodeOrName(String code, String name);

    List<DepartmentEntity> findAllByCodeOrName(String code, String name);
}
