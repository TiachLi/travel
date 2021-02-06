package travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.CategoryDao;
import travel.domain.Category;
import travel.util.JDBCUtils;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
      private   JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    public List<Category> findAll() {
        String sql = "select * from tab_category ";
        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }

    @Override
    public Category findOne(int cid) {
        String sql="SELECT  * FROM tab_category WHERE cid=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Category>(Category.class),cid);
    }

}
