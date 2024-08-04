package github.cheneykwok;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcMapping {

    /**
     * RPC path mapping &mdash; for example {@code "/path"}.
     */
    String value() default "";

}
