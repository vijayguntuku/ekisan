package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Cart;

import java.util.List;

public interface CartDao {
    List<Cart> findCartByID(int id);

    boolean saveOrUpdateCart(Cart cart);

    boolean deleteCart(int id);

    Cart findCartByUserIDAndProductId(int userId, int productId);
}
