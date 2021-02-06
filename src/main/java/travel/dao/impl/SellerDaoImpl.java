package travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.SellerDao;
import travel.domain.RouteImg;
import travel.domain.Seller;
import travel.util.JDBCUtils;

import java.util.List;

public class SellerDaoImpl implements SellerDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findOne(int sid) {
        String sql="SELECT * FROM tab_seller WHERE sid=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }


}
