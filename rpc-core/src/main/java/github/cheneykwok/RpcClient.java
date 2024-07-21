package github.cheneykwok;

import java.lang.annotation.Target;
import java.lang.annotation.*;

/**
 * 接口注解，声明应创建具有该接口的 RPC 客户端
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcClient {

    /**
     * 服务ID
     */
    String name() default "";
}
