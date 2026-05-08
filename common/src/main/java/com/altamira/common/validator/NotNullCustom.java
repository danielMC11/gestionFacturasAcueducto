package com.altamira.common.validator;

import com.altamira.common.validator.groups.OnPatchBatchUpdate;
import com.altamira.common.validator.groups.OnPutBatchUpdate;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@NotNull(groups = {OnPutBatchUpdate.class, OnPatchBatchUpdate.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface NotNullCustom {

    String message() default "ESTE CAMPO NO PUEDE SER NULO";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}