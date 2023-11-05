package com.xiaomizhou.admin.api;

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
public class ApiService {

    private final ApiRepository repository;

    public Page<Api> list(Api api, Pageable pageable) {
        Example<Api> example = Example.of(api);
        return repository.findAll(example, pageable);
    }

    public Api findById(Integer id) {
        return repository.findById(id).get();
    }

    public void save(Api api) {
        repository.save(api);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
