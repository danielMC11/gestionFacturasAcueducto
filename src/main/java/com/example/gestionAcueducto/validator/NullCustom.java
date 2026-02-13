package com.example.gestionAcueducto.validator;

import com.example.gestionAcueducto.validator.groups.*;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Null;

import java.lang.annotation.*;

@Null(groups = {OnCreate.class, OnPutSingleUpdate.class, OnPatchSingleUpdate.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface NullCustom {

    String message() default "ESTE CAMPO DEBE SER NULO";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}