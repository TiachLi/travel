package travel.service.impl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import travel.dao.CategoryDao;
import travel.dao.impl.CategoryDaoImpl;
import travel.domain.Category;
import travel.service.CategoryService;
import travel.util.JedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    CategoryDao categoryDao =new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {

        //先从redis中查询数据，查询不到再从数据库中查询，并把数据存入redis中
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> cs =null;
        if (categorys==null||categorys.size()==0){
            System.out.println("从数据库查询....");
            //如果为空,需要从数据库查询,在将数据存入redis
            //从数据库查询
            cs = categoryDao.findAll();
            //将集合数据存储到redis中的 category的key
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        }else {
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category =new Category();
                category.setCid((int) tuple.getScore());
                category.setCname(tuple.getElement());
                cs.add(category);
            }

        }

        return cs;
    }


}
