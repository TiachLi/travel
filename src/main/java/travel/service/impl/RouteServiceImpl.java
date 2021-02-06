package travel.service.impl;

import travel.dao.*;
import travel.dao.impl.*;
import travel.domain.*;
import travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private RouteDao routeDao =new RouteDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private FavoriteDao favoriteDao =new FavoriteDaoImpl();
    @Override
    public PageBean pageQuery(int cid, int currentPage, int pageSize, String rname) {
        PageBean pb =new PageBean();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        int totalCount =  routeDao.findTotalCount(cid,rname);

        pb.setTotalCount(totalCount);
        int start = (currentPage - 1) * pageSize;
        List<Route> routes = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(routes);

        int totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public Route findOne(String rid) {
        Route route =new Route();

        route=routeDao.findOne(Integer.parseInt(rid));

        List<RouteImg> imgs = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(imgs);

        Seller seller = sellerDao.findOne(route.getSid());
        route.setSeller(seller);

        Category category = categoryDao.findOne(route.getCid());
        route.setCategory(category);

        int count = favoriteDao.findCount(Integer.parseInt(rid));
        route.setCount(count);

        return route;
    }

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite= favoriteDao.findRouteByRidAndUid(Integer.parseInt(rid),uid);
        if (favorite==null){
            return false;
        }
        else {
            return true;
        }
    }
}
