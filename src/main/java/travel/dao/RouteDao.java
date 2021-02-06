package travel.dao;

import travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int findTotalCount(int cid, String rname);

    List<Route> findByPage(int cid, int start, int pageSize, String rname);

    Route findOne(int rid);
    //查询某uid的收藏分页
    List<Route> findPageFavorite(int[] rids);
    //查询被收藏的Route的分页
    List<Route> findAllFavorite(int[] rids);
    //更新被收藏次数
    void updateCount(int rid, int count);
}
