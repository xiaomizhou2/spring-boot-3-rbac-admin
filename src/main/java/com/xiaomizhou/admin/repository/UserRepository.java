package com.xiaomizhou.admin.repository;

import com.xiaomizhou.admin.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
