package com.wavemaker.ekisan.service;

import com.wavemaker.ekisan.dto.Orders;

import com.wavemaker.ekisan.dto.Response;

public interface OrdersService {
    Response findOrderById(int id);

    Response findAllOrders(int buyerId,int sellerId);

    Response saveOrUpdate(Orders orders);

}
