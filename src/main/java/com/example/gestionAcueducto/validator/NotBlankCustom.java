package com.example.gestionAcueducto.validator;

import com.example.gestionAcueducto.validator.groups.OnCreate;
import com.example.gestionAcueducto.validator.groups.OnPutBatchUpdate;
import com.example.gestionAcueducto.validator.groups.OnPutSingleUpdate;
import jakarta.validation.Constraint;
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