package github.cheneykwok.client;

import github.cheneykwok.client.properties.ClientProperties;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 接口注解，声明应创建具有该接口的 RPC 客户端
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcClient {

    @AliasFor("serverId")
    String value() default "";

    /**
     * 目标服务的ID
     *
     * <P>需配置{@link ClientProperties#serverAddrList}</P>
     */
    String serverId() default "";

    /**
     * 目标服务的地址（优先级高于 serverId）
     */
    String address() default "";

}
