package travel.dao;

import travel.domain.User;

public interface UserDao {
    boolean findUserByName(String name);

    boolean findUserByTel(String telephone);

    void save(User user);

    User findUserByUserNameAndPassword(String username, String password);
}
