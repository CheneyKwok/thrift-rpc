package github.cheneykwok;

import github.cheneykwok.api.User;
import github.cheneykwok.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@EnableRpcClient
@SpringBootApplication
public class ExampleClient {

    private static final Logger log = LoggerFactory.getLogger(ExampleClient.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExampleClient.class);
        UserService userService = context.getBean(UserService.class);
        User user = new User();
        user.setUserId(2);
        user.setUsername("李四");
        log.info("username: {}", userService.getUser(1, "张三", user).toString());
    }
}