package com.wesp.infra.anotaçãoDeValidação;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)  // Referência ao validador
public @interface UniqueEmail {

    String message() default "Email is already in use";  // Mensagem de erro padrão

    Class<?>[] groups() default {};  // Para agrupar restrições

    Class<? extends Payload>[] payload() default {};  // Para transportar informações de payload
}

