package com.xiaomizhou.admin.service;

import com.xiaomizhou.admin.entity.UserEntity;
import com.xiaomizhou.admin.repository.UserRepository;
import com.xiaomizhou.admin.service.validation.NotConflictUser;
import com.xiaomizhou.admin.service.validation.UniqueUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Page<UserEntity> page(UserEntity userEntity, Pageable pageable) {
        Example<UserEntity> example = Example.of(userEntity);
        return repository.findAll(example, pageable);
    }

    public UserEntity findById(Integer id) {
        Optional<UserEntity> optional = repository.findById(id);
        return optional.get();
    }

    public void create(UserEntity user) {
        repository.save(user);
    }

    public void update(UserEntity user) {
        repository.save(user);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
