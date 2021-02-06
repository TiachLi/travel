package travel.dao.impl;

import com.alibaba.druid.util.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.FavoriteDao;
import travel.domain.Favorite;
import travel.domain.Route;
import travel.util.JDBCUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template =new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findRouteByRidAndUid(int rid, int uid) {
        String sql="SELECT * FROM tab_favorite WHERE  rid=? and uid=?";
        Favorite favorite = null;
        try {
            favorite= template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        }catch (DataAccessException e){
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public int findCount(int rid) {
        String sql="select  count(*) from tab_favorite where rid=?";
        return template.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void add(int rid, Date date, int uid) {
        String sql="insert into tab_favorite value(?,?,?)";
        template.update(sql,rid,date,uid);
    }

    @Override
    public int[] findRidsByUid(int uid) {
        String sql="select * from tab_favorite where uid=?";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), uid);

        int length =list.size();

        int[] ints =new int[length];
        for (int i = 0; i <length ; i++) {
            ints[i]=list.get(i).getRid();
        }

        return ints;
    }

    @Override
    public int[] findFavoriteDistinct() {
        String sql="select * from tab_favorite group by rid";
        List<Route> routes = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        int length =routes.size();
        int[] ints =new int[length];
        for (int i = 0; i <length ; i++) {
            ints[i]=routes.get(i).getRid();
        }
        return ints;
    }


}
