package com.xiaomizhou.admin.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xiaomizhou
 * @date 2023/10/27
 * @email 521jx123@gmail.com
 */
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

    boolean existsByTitleOrPath(String title, String path);

    List<MenuEntity> findAllByTitleOrPath(String title, String path);
}
