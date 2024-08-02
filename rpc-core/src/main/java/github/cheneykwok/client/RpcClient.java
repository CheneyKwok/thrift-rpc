package github.cheneykwok.client;

import java.lang.annotation.*;

/**
 * 接口注解，声明应创建具有该接口的 RPC 客户端
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcClient {

    /**
     * 服务端ID
     */
    String serverId() default "";

    String prefix() default "";
}
