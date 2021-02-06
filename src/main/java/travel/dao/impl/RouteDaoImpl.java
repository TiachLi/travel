package travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.RouteDao;
import travel.domain.Route;
import travel.util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
        String sql="SELECT  COUNT(*) FROM tab_route where 1=1  ";

        StringBuilder sb =new StringBuilder(sql);
        List parameters = new ArrayList();
        //拼接sql
        if (cid!=0){
        sb.append(" and cid=? ");
        parameters.add(cid);
        }
        if (rname!=null){
          sb.append(" and rname like ? ");
          parameters.add("%"+rname+"%");

        }
        sql=sb.toString();
        return  template.queryForObject(sql, Integer.class, parameters.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        String sql="SELECT * FROM tab_route  where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List parameters = new ArrayList();//条件们
        //拼接sql
        if(cid != 0){
            sb.append( " and cid = ? ");
            parameters.add(cid);//添加？对应的值
        }


        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            parameters.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件

        parameters.add(start);
        parameters.add(pageSize);

        sql=sb.toString();
        List<Route> list=null;
        try {
           list= template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), parameters.toArray());
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Route findOne(int rid) {
        String sql ="SELECT * FROM tab_route where rid=?";
      return   template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
    //查询某uid的收藏分页
    @Override
    public List<Route> findPageFavorite(int[] rids) {
        List<Route> routeList =new ArrayList<Route>();
        String sql="select * from tab_route where rid=?";
        int length =rids.length;
        for (int i = 0; i <length ; i++) {
            Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rids[i]);
            routeList.add(route);
        }

        return routeList;
    }
    //查询被收藏的route
    @Override
    public List<Route> findAllFavorite(int[] rids) {
        List<Route> routeList =new ArrayList<Route>();
        String sql="select * from tab_route where rid=?";
        int length =rids.length;
        for (int i = 0; i <length ; i++) {
            Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rids[i]);
            routeList.add(route);
        }
        return routeList;
    }

    @Override
    public void updateCount(int rid, int count) {
        String sql="update tab_route set count=? where rid=?";
        template.update(sql,count,rid);
    }
}
