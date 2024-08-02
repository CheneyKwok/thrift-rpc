package github.cheneykwok.server;

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
public @interface EnableRpcService {

    String[] basePackages() default {};

}
