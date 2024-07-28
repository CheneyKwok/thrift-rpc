package github.cheneykwok.api;

import github.cheneykwok.RpcRequestInterceptor;
import github.cheneykwok.RpcRequestTemplate;
import github.cheneykwok.thrift.ServiceProperty;
import github.cheneykwok.thrift.gen.task.StatusTaskRequest;
import github.cheneykwok.thrift.gen.task.StatusTaskRespon;
import github.cheneykwok.thrift.gen.task.TaskRpcService;
import github.cheneykwok.thrift.pool.ConnectionKey;
import github.cheneykwok.thrift.pool.ThriftConnectionPool;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author gzc
 * @date 2024-07-26
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ThriftConnectionPool connectionPool;

    @Autowired(required = false)
    private List<RpcRequestInterceptor> interceptors;

    @Override
    public StatusTaskRespon addTask(StatusTaskRequest taskRequest) {
        if (interceptors != null) {
            RpcRequestTemplate template = new RpcRequestTemplate();
            taskRequest.setHeader(new HashMap<>());
            template.setHeader(taskRequest.getHeader());
            interceptors.forEach(interceptor -> interceptor.apply(template));
        }
        ConnectionKey connectionKey = new ConnectionKey();
        connectionKey.setConnectTimeout(10000);
        connectionKey.setServiceProperty(new ServiceProperty("user", "localhost", 7777));
        connectionKey.setTServiceClientClass(TaskRpcService.Client.class);
        StatusTaskRespon statusTaskRespon = null;
        TaskRpcService.Client client = null;
        try {
            client = (TaskRpcService.Client) connectionPool.borrowObject(connectionKey);
            statusTaskRespon = client.StatusTask(taskRequest);
        } catch (TException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.returnObject(connectionKey, client);
        }
        return statusTaskRespon;

    }
}
