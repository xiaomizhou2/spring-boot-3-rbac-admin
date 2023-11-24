package com.xiaomizhou.admin.domain.role.validation;

import com.xiaomizhou.admin.domain.role.RoleEntity;
import com.xiaomizhou.admin.domain.role.RoleRepository;
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
public class RoleValidation<T extends Annotation> implements ConstraintValidator<T, RoleEntity> {
    @Resource
    protected RoleRepository repository;
    protected Predicate<RoleEntity> predicate = c -> true;

    @Override
    public boolean isValid(RoleEntity entity, ConstraintValidatorContext context) {
        return repository == null || predicate.test(entity);
    }

    public static class UniqueRoleValidator extends RoleValidation<UniqueRole> {
        @Override
        public void initialize(UniqueRole constraintAnnotation) {
            predicate = c -> !repository.existsByCodeOrName(c.getCode(), c.getName());
        }
    }

    public static class NotConflictRoleValidator extends RoleValidation<NotConflictRole> {
        @Override
        public void initialize(NotConflictRole constraintAnnotation) {
            predicate = c -> {
                List<RoleEntity> list = repository.findByCodeOrEmail(c.getCode(), c.getName());
                return list.isEmpty() || (list.size() == 1 && list.get(0).getId().equals(c.getId()));
            };
        }
    }
}
