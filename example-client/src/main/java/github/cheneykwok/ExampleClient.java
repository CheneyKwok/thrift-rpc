package github.cheneykwok;

import github.cheneykwok.api.User;
import github.cheneykwok.api.UserRpcService;
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
        UserRpcService userRpcService = context.getBean(UserRpcService.class);
        User user = new User();
        user.setUserId(2);
        user.setUsername("李四");
        log.info("username: {}", userRpcService.getUser(1, "张三", user).toString());
//        TaskService taskService = context.getBean(TaskService.class);
//        StatusTaskRequest request = new StatusTaskRequest();
//        request.setTaskName("xxx");
//        request.setSrcInnerId(1);
//        request.setStoreSysOutId("xx");
//        request.setTaskRunDate(1111);
//        request.setTaskType(2);
//        request.setAfterStatus(1);
//        request.setBeforeStatus(0);
//        request.setIsDuplicate(false);
//        taskService.addTask(request);
    }
}