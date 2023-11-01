package com.xiaomizhou.admin.user;

import com.xiaomizhou.admin.dept.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void update(Integer id, UserRequest request) {
        Department department = Department.builder().id(request.getDeptId()).build();
        User user = User.builder().id(id)
                .status(request.getStatus() == null ? 0 : request.getStatus())
                .department(department)
                .build();
        repository.save(user);
    }

    public User findById(Integer id) {
        Optional<User> optional = repository.findById(id);
        return optional.get();
    }

    public Page<User> page(Map<String, Object> query, Pageable pageable) {
        return repository.findAll(pageable);
    }
}
