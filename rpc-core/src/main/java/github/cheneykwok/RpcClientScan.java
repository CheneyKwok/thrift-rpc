package github.cheneykwok;

import github.cheneykwok.client.RpcClientScannerRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

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
@Import(RpcClientScannerRegistrar.class)
public @interface RpcClientScan {

    /**
     * 属性的 basePackages() 别名
     */
    @AliasFor("basePackages")
    String[] value() default {};

    /**
     * 用于扫描 Rpc Client 接口的基础包
     */
    @AliasFor("value")
    String[] basePackages() default {};

}
