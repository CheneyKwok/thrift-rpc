package github.cheneykwok;

import github.cheneykwok.server.EnableRpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableRpcService
@SpringBootApplication
public class ExampleServer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ExampleServer.class, args);
    }
}