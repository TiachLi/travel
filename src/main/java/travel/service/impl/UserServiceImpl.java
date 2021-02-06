package travel.service.impl;

import travel.dao.UserDao;
import travel.dao.impl.UserDaoImpl;
import travel.domain.User;
import travel.service.UserService;

public class UserServiceImpl implements UserService {
    UserDao dao =new UserDaoImpl();
    @Override
    public String register(User user) {
       boolean flag_name=dao.findUserByName(user.getName());
       boolean flag_tel=dao.findUserByTel(user.getTelephone());
        if (flag_name){
            return "name_error";
        }
       if (flag_tel){
            return "tel_error";
        }
       dao.save(user);
       return "success";



    }

    @Override
    public User login(User user) {

        return dao.findUserByUserNameAndPassword(user.getUsername(),user.getPassword());
    }
}
