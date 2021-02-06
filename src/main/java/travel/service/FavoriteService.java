package travel.service;

import travel.domain.PageBean;
import travel.domain.Route;

public interface FavoriteService {

    void add(String rid, int uid);

    PageBean<Route> FavoritePageQuery(int uid,PageBean pageBean,int currentPage);

    PageBean<Route> RankPageQuery(PageBean<Route> pageBean, int currentPage);
}
