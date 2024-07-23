package github.cheneykwok.api;

import github.cheneykwok.RpcClient;

@RpcClient(name = "user-service")
public interface UserService {

    User getUser(Integer userId, String username);
}
