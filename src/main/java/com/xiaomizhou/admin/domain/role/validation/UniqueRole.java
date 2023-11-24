package com.xiaomizhou.admin.domain.role.validation;

import com.xiaomizhou.admin.domain.user.validation.UserValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/24
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, TYPE})
@Constraint(validatedBy = RoleValidation.UniqueRoleValidator.class)
public @interface UniqueRole {
    String message() default "角色编码、名称均不允许与现存角色重复";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
