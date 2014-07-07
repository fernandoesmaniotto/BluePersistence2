package br.com.bluepersistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target (ElementType.FIELD)
@Retention (RetentionPolicy.RUNTIME)

public @interface DroidPrimaryKey {
	public boolean obrigatorio() default false;
	public boolean criptografia() default false;
}
