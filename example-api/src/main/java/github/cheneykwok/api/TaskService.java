package github.cheneykwok.api;

import github.cheneykwok.RpcClient;
import github.cheneykwok.thrift.gen.task.StatusTaskRequest;
import github.cheneykwok.thrift.gen.task.StatusTaskRespon;

/**
 * @author gzc
 * @date 2024-07-26
 */
@RpcClient(name = "user-service")
public interface TaskService {

    StatusTaskRespon addTask(StatusTaskRequest taskRequest);
}
