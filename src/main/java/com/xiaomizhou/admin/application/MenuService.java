package com.xiaomizhou.admin.application;

import com.xiaomizhou.admin.domain.menu.MenuEntity;
import com.xiaomizhou.admin.domain.menu.MenuRepository;
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
public class MenuService {

    private final MenuRepository repository;

    public Page<MenuEntity> list(MenuEntity menuEntity, Pageable pageable) {
        Example<MenuEntity> example = Example.of(menuEntity);
        return repository.findAll(example, pageable);
    }

    public MenuEntity findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("数据不存在"));
    }

    public void save(MenuEntity menuEntity) {
        repository.save(menuEntity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
