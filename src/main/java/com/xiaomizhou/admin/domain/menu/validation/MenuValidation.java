package com.xiaomizhou.admin.domain.menu.validation;

import com.xiaomizhou.admin.domain.menu.MenuEntity;
import com.xiaomizhou.admin.domain.menu.MenuRepository;
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
public class MenuValidation<T extends Annotation> implements ConstraintValidator<T, MenuEntity> {
    @Resource
    protected MenuRepository repository;
    protected Predicate<MenuEntity> predicate = c -> true;

    @Override
    public boolean isValid(MenuEntity entity, ConstraintValidatorContext constraintValidatorContext) {
        return repository == null || predicate.test(entity);
    }

    public static class UniqueMenuValidation extends MenuValidation<UniqueMenu> {
        @Override
        public void initialize(UniqueMenu constraintAnnotation) {
            predicate = c -> !repository.existsByTitleOrPath(c.getTitle(), c.getPath());
        }
    }

    public static class NotConflictValidation extends MenuValidation<NotConflictMenu> {
        @Override
        public void initialize(NotConflictMenu constraintAnnotation) {
            predicate = c -> {
                List<MenuEntity> list = repository.findAllByTitleOrPath(c.getTitle(), c.getPath());
                return list.isEmpty() || (list.size() == 1 && list.get(0).getId().equals(c.getId()));
            };
        }
    }
}
