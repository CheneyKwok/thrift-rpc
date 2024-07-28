package github.cheneykwok;

import github.cheneykwok.api.TaskService;
import github.cheneykwok.thrift.gen.task.StatusTaskRequest;
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
//        UserService userService = context.getBean(UserService.class);
//        User user = new User();
//        user.setUserId(2);
//        user.setUsername("李四");
//        log.info("username: {}", userService.getUser(1, "张三", user).toString());
        TaskService taskService = context.getBean(TaskService.class);
        StatusTaskRequest request = new StatusTaskRequest();
        request.setTaskName("xxx");
        request.setSrcInnerId(1);
        request.setStoreSysOutId("xx");
        request.setTaskRunDate(1111);
        request.setTaskType(2);
        request.setAfterStatus(1);
        request.setBeforeStatus(0);
        request.setIsDuplicate(false);
        taskService.addTask(request);
    }
}