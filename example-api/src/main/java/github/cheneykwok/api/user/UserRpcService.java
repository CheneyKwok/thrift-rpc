package github.cheneykwok.api.user;

import github.cheneykwok.RpcMapping;
import github.cheneykwok.client.RpcClient;

@RpcClient(serverId = "user", address = "127.0.0.1:40880")
@RpcMapping("/user")
public interface UserRpcService {

    @RpcMapping("/getUser")
    User getUser(Integer userId, String username, User user);


}
