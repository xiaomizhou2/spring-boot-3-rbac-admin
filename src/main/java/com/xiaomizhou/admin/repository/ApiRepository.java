package com.xiaomizhou.admin.repository;

import com.xiaomizhou.admin.entity.ApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xiaomizhou
 * @date 2023/10/27
 * @email 521jx123@gmail.com
 */
public interface ApiRepository extends JpaRepository<ApiEntity, Integer> {
}
