package com.xiaomizhou.admin.application;

import com.xiaomizhou.admin.domain.department.DepartmentEntity;
import com.xiaomizhou.admin.domain.user.UserEntity;
import com.xiaomizhou.admin.domain.user.UserRepository;
import com.xiaomizhou.admin.interfaces.vo.UserQueryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Page<UserEntity> page(UserQueryVo query, Pageable pageable) {
        DepartmentEntity department = DepartmentEntity.builder()
                .id(query.getDeptId())
                .build();

        UserEntity user = UserEntity.builder()
                .username(query.getUsername())
                .name(query.getName())
                .email(query.getEmail())
                .department(department)
                .build();
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                // 用户名模糊查询
                .withMatcher("username", ExampleMatcher.GenericPropertyMatcher::startsWith)
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::startsWith)
                .withMatcher("email", ExampleMatcher.GenericPropertyMatcher::startsWith)
                // 密码忽略查询
                .withIgnorePaths("password");
        Example<UserEntity> example = Example.of(user, exampleMatcher);
        return repository.findAll(example, pageable);
    }

    public UserEntity findById(Integer id) {
        Optional<UserEntity> optional = repository.findById(id);
        return optional.get();
    }

    public void save(UserEntity user) {
        repository.save(user);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
