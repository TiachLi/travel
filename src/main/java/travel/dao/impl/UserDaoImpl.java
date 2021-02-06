package travel.dao.impl;

import travel.domain.User;
import travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.UserDao;

public class UserDaoImpl implements UserDao  {

    JdbcTemplate template =new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean findUserByName(String name) {
        String sql="SELECT  * FROM tab_user WHERE name=?";
        try{
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),name);
            if (user!=null){
                return true;
            }
            else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

      return false;
    }

    @Override
    public boolean findUserByTel(String telephone) {
        String sql="SELECT  *FROM tab_user WHERE telephone=?";
        try{
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),telephone);
            if (user!=null){
                return true;
            }
            else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public void save(User user) {
        //1.定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //2.执行sql

        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        );
    }

    @Override
    public User findUserByUserNameAndPassword(String username, String password) {
        User user = null;
        try {
            //1.定义sql
            String sql = "select * from tab_user where username = ? and password = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
        } catch (Exception e) {

        }
        return user;
    }




}
