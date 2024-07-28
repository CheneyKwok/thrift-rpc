package github.cheneykwok.api;

import github.cheneykwok.thrift.gen.task.StatusTaskRequest;
import github.cheneykwok.thrift.gen.task.StatusTaskRespon;

/**
 * @author gzc
 * @date 2024-07-26
 */
public interface TaskService{

    StatusTaskRespon addTask(StatusTaskRequest taskRequest);
}
