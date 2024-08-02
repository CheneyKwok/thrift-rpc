package github.cheneykwok.server;

import github.cheneykwok.thrift.gen.task.StatusTaskRequest;
import github.cheneykwok.thrift.gen.task.StatusTaskRespon;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

@Service
public class TaskRpcService implements github.cheneykwok.thrift.gen.task.TaskRpcService.Iface {

    @Override
    public StatusTaskRespon StatusTask(StatusTaskRequest request) throws TException {
        String traceId = request.getHeader().get("traceId");
        System.out.println("..................:" + traceId);
        return new StatusTaskRespon();
    }
}
