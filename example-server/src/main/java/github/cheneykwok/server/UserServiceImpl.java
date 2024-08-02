package github.cheneykwok.server;

import github.cheneykwok.api.User;
import github.cheneykwok.api.UserRpcService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserRpcService {

    @Override
    public User getUser(Integer userId, String username, User user) {
        user.setUserId(userId);
        user.setUsername(username);
        return user;
    }
}
