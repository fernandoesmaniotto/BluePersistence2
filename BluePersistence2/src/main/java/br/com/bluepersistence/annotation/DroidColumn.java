package br.com.bluepersistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target (ElementType.FIELD)
@Retention (RetentionPolicy.RUNTIME)

public @interface DroidColumn {
    public String tipo();
    public int tamanho() default 0; 
    public boolean obrigatorio() default false;
    public boolean chave() default false;
    public String tamanhoPrecisao() default "";
    public boolean criptografia() default false;
}
