package github.cheneykwok.api;

import github.cheneykwok.RpcMapping;
import github.cheneykwok.client.RpcClient;

@RpcClient(serverId = "user")
@RpcMapping("/user")
public interface UserRpcService {

    @RpcMapping("/getUser")
    User getUser(Integer userId, String username, User user);


}
