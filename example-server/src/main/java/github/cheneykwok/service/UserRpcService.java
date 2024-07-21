package github.cheneykwok.service;

import github.cheneykwok.RpcService;
import github.cheneykwok.api.UserService;

@RpcService
public class UserRpcService implements UserService {

    @Override
    public String getUserName() {
        return "张三";
    }
}
