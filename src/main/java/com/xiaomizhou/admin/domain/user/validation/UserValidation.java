package com.xiaomizhou.admin.domain.user.validation;

import com.xiaomizhou.admin.domain.user.UserEntity;
import com.xiaomizhou.admin.domain.user.UserRepository;
import jakarta.annotation.Resource;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/5
 */
public class UserValidation<T extends Annotation> implements ConstraintValidator<T, UserEntity> {

    @Resource
    protected UserRepository repository;
    protected Predicate<UserEntity> predicate = c -> true;

    @Override
    public boolean isValid(UserEntity value, ConstraintValidatorContext context) {
        return repository == null || predicate.test(value);
    }

    public static class UniqueUserValidator extends UserValidation<UniqueUser> {
        public void initialize(UniqueUser constraintAnnotation) {
            predicate = c -> !repository.existsByUsernameOrEmail(c.getUsername(), c.getEmail());
        }
    }

    public static class NotConflictUserValidator extends UserValidation<NotConflictUser> {
        public void initialize(NotConflictUser constraintAnnotation) {
            predicate = c -> {
                List<UserEntity> collection = repository.findByUsernameOrEmail(c.getUsername(), c.getEmail());
                // 将用户名、邮件改成与现有完全不重复的，或者只与自己重复的，就不算冲突
                return collection.isEmpty() || (collection.size() == 1 && collection.iterator().next().getId().equals(c.getId()));
            };
        }
    }
}
