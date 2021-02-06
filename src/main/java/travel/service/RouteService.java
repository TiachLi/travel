package travel.service;

import travel.domain.PageBean;
import travel.domain.Route;

public interface RouteService {
    PageBean pageQuery(int cid, int currentPage, int pageSize, String rname);

    Route findOne(String rid);

    boolean isFavorite(String rid, int uid);
}
