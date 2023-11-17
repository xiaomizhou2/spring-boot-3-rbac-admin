package com.xiaomizhou.admin.application;

import com.xiaomizhou.admin.domain.api.ApiEntity;
import com.xiaomizhou.admin.domain.api.ApiRepository;
import com.xiaomizhou.admin.interfaces.vo.ApiQueryVo;
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
public class ApiService {

    private final ApiRepository repository;

    public Page<ApiEntity> list(ApiQueryVo query, Pageable pageable) {
        ApiEntity api = ApiEntity.builder()
                .url(query.getUrl())
                .method(query.getMethod())
                .build();

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("url", ExampleMatcher.GenericPropertyMatcher::startsWith);

        Example<ApiEntity> example = Example.of(api, exampleMatcher);
        return repository.findAll(example, pageable);
    }

    public ApiEntity findById(Integer id) {
        return repository.findById(id).get();
    }

    public void save(ApiEntity apiEntity) {
        repository.save(apiEntity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
