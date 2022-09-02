package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Cart;

public interface CartDao {
    Cart findCartByID(int id);

    boolean saveOrUpdateCart(Cart cart);

    boolean deleteCart(int id);

    Cart findCartByUserIDAndProductId(int userId, int productId);
}
