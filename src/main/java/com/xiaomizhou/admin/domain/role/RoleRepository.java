package com.xiaomizhou.admin.domain.role;

import com.xiaomizhou.admin.domain.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xiaomizhou
 * @date 2023/10/27
 * @email 521jx123@gmail.com
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    boolean existsByCodeOrName(String code, String name);

    List<RoleEntity> findByCodeOrEmail(String code, String email);
}
