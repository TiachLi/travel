import org.springframework.jdbc.core.BeanPropertyRowMapper;
import travel.domain.Route;
import travel.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class jdbc {
    JdbcTemplate template =new JdbcTemplate(JDBCUtils.getDataSource());

    @Test
    public void test1(){
      template =new JdbcTemplate(JDBCUtils.getDataSource());
        String sql ="SELECT * FROM test";
        List<Map<String, Object>> maps = template.queryForList(sql);
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }

    @Test
    public void test2(){
        String sql="UPDATE tab_route SET cid=? where rid=?";

        for (int j = 1; j <=8; j++) {
            for (int i =60*(j-1)+1; i <=60*j; i++) {
                template.update(sql,j,i);
            }
        }


    }

    @Test
    public void test3(){
        String sql="select * from tab_favorite where uid=?";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), 1);

        int length =list.size();

        int[] ints =new int[length];
        for (int i = 0; i <length ; i++) {
            ints[i]=list.get(i).getRid();
            System.out.println(ints[i]);
        }

    }

    @Test
    public void test4(){
        String sql="select * from tab_favorite group by rid";
        List<Route> routes = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        System.out.println(routes.size());
        for (Route route : routes) {
            System.out.println(route.getRid());
        }

    }
}
