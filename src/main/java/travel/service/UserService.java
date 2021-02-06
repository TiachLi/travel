package travel.service;

import travel.domain.User;

public interface UserService {
    String register(User user);

    User login(User user);
}
