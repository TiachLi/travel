package travel.dao;

import travel.domain.Seller;

public interface SellerDao {
    Seller findOne(int sid);
}
