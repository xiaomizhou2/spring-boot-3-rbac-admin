package com.xiaomizhou.admin.domain.permission;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xiaomizhou
 * @date 2023/10/27
 * @email 521jx123@gmail.com
 */
public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {

    boolean existsByCodeOrName(String code, String name);

    List<PermissionEntity> findAllByCodeOrName(String code, String name);
}
