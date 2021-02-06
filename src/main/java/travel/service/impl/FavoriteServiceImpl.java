package travel.service.impl;

import travel.dao.FavoriteDao;
import travel.dao.RouteDao;
import travel.dao.impl.FavoriteDaoImpl;
import travel.dao.impl.RouteDaoImpl;
import travel.domain.PageBean;
import travel.domain.Route;
import travel.service.FavoriteService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    FavoriteDao favoriteDao =new FavoriteDaoImpl();
    RouteDao routeDao =new RouteDaoImpl();
    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),new Date(),uid);
        int count = favoriteDao.findCount(Integer.parseInt(rid));
        //更新该rid被收藏数
        routeDao.updateCount(Integer.parseInt(rid),count);
    }

    //我的收藏封装分页对象
    @Override
    public PageBean<Route> FavoritePageQuery(int uid,PageBean pageBean,int currentPage) {
        int[] rids=favoriteDao.findRidsByUid(uid);
        //总记录数
        pageBean.setTotalCount(rids.length);
        //每页条数，固定12
        pageBean.setPageSize(12);
        //总页数
        int totalPage=rids.length%12==0?rids.length/12:rids.length/12+1;
        pageBean.setTotalPage(totalPage);

        //当前页码数
        pageBean.setCurrentPage(currentPage);
        //每页显示的数据集合
        List<Route> routesAll=routeDao.findPageFavorite(rids);
        List<Route> routes=new ArrayList<Route>();
        for (int i = (currentPage-1)*12; i <(currentPage-1)*12+12 ; i++) {
            if (i<routesAll.size()){
                routes.add(routesAll.get(i));
            }
        }
        pageBean.setList(routes);

        return pageBean;
    }
    //收藏排行封装分页对象
    @Override
    public PageBean<Route> RankPageQuery(PageBean<Route> pageBean, int currentPage) {
        //被收藏的Route的总记录数
        int[] favorites =favoriteDao.findFavoriteDistinct();
        int totalCount = favorites.length;

        pageBean.setTotalCount(totalCount);
        //每页条数，固定8
        pageBean.setPageSize(8);
        //总页数
        int totalPage=totalCount%8==0?totalCount/8:totalCount/8+1;
        pageBean.setTotalPage(totalPage);

        //当前页码数
        pageBean.setCurrentPage(currentPage);
        //每页显示的数据集合
        List<Route> routesAll=routeDao.findAllFavorite(favorites);
        routesAll.sort(new rankComplare());

        for (Route route : routesAll) {
            System.out.println(route.getCount());
        }

        List<Route> routes=new ArrayList<Route>();
        for (int i = (currentPage-1)*8; i <(currentPage-1)*8+8 ; i++) {
            if (i<routesAll.size()){
                routes.add(routesAll.get(i));
            }
        }
        pageBean.setList(routes);
        return pageBean;
    }
  public class rankComplare implements Comparator<Route> {
      @Override
      public int compare(Route o1, Route o2) {
          return o2.getCount()-o1.getCount();
      }
  }

}
