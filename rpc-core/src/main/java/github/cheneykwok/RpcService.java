package github.cheneykwok;

import org.springframework.stereotype.Service;

import java.lang.annotation.Target;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface RpcService {

}
