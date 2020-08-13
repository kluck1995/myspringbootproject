package com.dk.common.components.easyexcel.annotation;

import com.dk.common.components.easyexcel.annotation.validator.SensitiveCharactersValidator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = SensitiveCharactersValidator.class) // 指定这个注解的验证者类
public @interface SensitiveCharactersValidate {
    /**
     * 默认错误消息
     */
    String message() default "不能包含敏感字符";


    /**
     * 分组
     * @return
     */
    Class<?>[] groups() default {};

    /**
     * 负载
     */
    Class<? extends Payload>[] payload() default {};
}
