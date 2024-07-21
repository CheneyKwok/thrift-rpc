package github.cheneykwok;

import github.cheneykwok.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableRpcClient
@SpringBootApplication
public class ExampleClient {

    private static final Logger log = LoggerFactory.getLogger(ExampleClient.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ExampleClient.class, args);
        UserService userService = context.getBean(UserService.class);
        log.info("username: {}", userService.getUserName());
    }
}