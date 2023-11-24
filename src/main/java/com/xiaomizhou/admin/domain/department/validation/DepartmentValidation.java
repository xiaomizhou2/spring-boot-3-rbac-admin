package com.xiaomizhou.admin.domain.department.validation;

import com.xiaomizhou.admin.domain.department.DepartmentEntity;
import com.xiaomizhou.admin.domain.department.DepartmentRepository;
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
public class DepartmentValidation<T extends Annotation> implements ConstraintValidator<T, DepartmentEntity> {
    @Resource
    protected DepartmentRepository repository;
    protected Predicate<DepartmentEntity> predicate = c -> true;

    @Override
    public boolean isValid(DepartmentEntity entity, ConstraintValidatorContext constraintValidatorContext) {
        return repository == null || predicate.test(entity);
    }

    public static class UniqueDepartmentValidation extends DepartmentValidation<UniqueDepartment> {
        @Override
        public void initialize(UniqueDepartment constraintAnnotation) {
            predicate = c -> !repository.existsByCodeOrName(c.getCode(), c.getName());
        }
    }

    public static class NotConflictDepartmentValidation extends DepartmentValidation<NotConflictDepartment> {
        @Override
        public void initialize(NotConflictDepartment constraintAnnotation) {
            predicate = c -> {
                List<DepartmentEntity> list = repository.findAllByCodeOrName(c.getCode(), c.getName());
                return list.isEmpty() || (list.size() == 1 && list.get(0).getId().equals(c.getId()));
            };
        }
    }
}
