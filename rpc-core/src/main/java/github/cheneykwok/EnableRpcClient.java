package github.cheneykwok;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Target;
import java.lang.annotation.*;

/**
 * 扫描声明为 RPC 客户端的接口
 *
 * @author gzc
 * @date 2024-07-21
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RpcClientsRegistrar.class)
public @interface EnableRpcClient {

    String[] basePackages() default {};

}
