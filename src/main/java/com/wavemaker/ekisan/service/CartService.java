package com.wavemaker.ekisan.service;

import com.wavemaker.ekisan.dto.Cart;
import com.wavemaker.ekisan.dto.Response;

public interface CartService {
    Response findCartById(int id);

    Response saveOrUpdateCart(Cart cart);

    Response deleteCart(int id);
}
