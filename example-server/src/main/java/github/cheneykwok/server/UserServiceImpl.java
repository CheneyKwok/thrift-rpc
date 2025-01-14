package github.cheneykwok.server;

import github.cheneykwok.api.user.User;
import github.cheneykwok.api.user.UserRpcService;
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
