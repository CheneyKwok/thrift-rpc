package github.cheneykwok.service;

import github.cheneykwok.RpcService;
import github.cheneykwok.api.User;
import github.cheneykwok.api.UserService;

@RpcService
public class UserRpcService implements UserService {

    @Override
    public User getUser(Integer userId, String username, User user) {
        user.setUserId(userId);
        user.setUsername(username);
        return user;
    }
}
