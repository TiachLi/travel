package travel.dao;

import travel.domain.Favorite;
import travel.domain.Route;

import java.util.Date;
import java.util.List;

public interface FavoriteDao {
    Favorite findRouteByRidAndUid(int rid, int uid);

    int findCount(int rid);

    void add(int rid, Date date, int uid);

    int[] findRidsByUid(int uid);
    //查找不重复的被收藏的Route
    int[] findFavoriteDistinct();
}
