package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Cart;
import com.wavemaker.ekisan.dto.Orders;
import com.wavemaker.ekisan.dto.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrdersDao {
	Orders findOrderByID(int id);

	List<Orders> findAllOrders(int buyerId, int sellerId);

	public boolean saveOrUpdate(Orders orders);

	boolean checkOutOrder(int id, Map<Integer, List<Cart>> cartMap, int addressId) throws SQLException;
}
