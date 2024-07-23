package github.cheneykwok;

import github.cheneykwok.service.EnableRpcService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@EnableRpcService
@SpringBootApplication
public class ExampleServer {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(ExampleServer.class);
    }
}