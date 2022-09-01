package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Orders;
import com.wavemaker.ekisan.dto.Product;

import java.util.List;

public interface OrdersDao {
  Orders findOrderByID(int id);

  List<Orders> findAllOrders();
}
