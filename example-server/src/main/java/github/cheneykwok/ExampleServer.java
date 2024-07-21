package github.cheneykwok;

import github.cheneykwok.service.EnableRpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRpcService
@SpringBootApplication
public class ExampleServer {

    public static void main(String[] args) {
        SpringApplication.run(ExampleServer.class, args);
    }
}