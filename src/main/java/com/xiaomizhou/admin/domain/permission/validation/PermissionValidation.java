package com.xiaomizhou.admin.domain.permission.validation;

import com.xiaomizhou.admin.domain.permission.PermissionEntity;
import com.xiaomizhou.admin.domain.permission.PermissionRepository;
import jakarta.annotation.Resource;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/24
 */
public class PermissionValidation<T extends Annotation> implements ConstraintValidator<T, PermissionEntity> {
    @Resource
    protected PermissionRepository repository;
    protected Predicate<PermissionEntity> predicate = c -> true;

    @Override
    public boolean isValid(PermissionEntity entity, ConstraintValidatorContext constraintValidatorContext) {
        return repository == null || predicate.test(entity);
    }

    public static class UniquePermissionValidator extends PermissionValidation<UniquePermission> {
        @Override
        public void initialize(UniquePermission constraintAnnotation) {
            predicate = c -> !repository.existsByCodeOrName(c.getCode(), c.getName());
        }
    }

    public static class NotConflictPermissionValidator extends PermissionValidation<NotConflictPermission> {
        @Override
        public void initialize(NotConflictPermission constraintAnnotation) {
            predicate = c -> {
                List<PermissionEntity> list = repository.findAllByCodeOrName(c.getCode(), c.getName());
                return list.isEmpty() || (list.size() == 1 && list.get(0).getId().equals(c.getId()));
            };
        }
    }
}
