package com.altamira.common.validator;

import com.altamira.common.validator.groups.OnCreate;
import com.altamira.common.validator.groups.OnPutBatchUpdate;
import com.altamira.common.validator.groups.OnPutSingleUpdate;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.*;

@NotBlank(groups = {OnCreate.class, OnPutSingleUpdate.class, OnPutBatchUpdate.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotBlankCustom {

    String message() default "ESTE CAMPO NO PUEDE SER VACÍO";

    Class<?>[] groups() default {OnCreate.class, OnPutSingleUpdate.class, OnPutBatchUpdate.class};

    Class<? extends Payload>[] payload() default {};
}