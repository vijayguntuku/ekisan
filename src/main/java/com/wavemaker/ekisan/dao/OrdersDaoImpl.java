package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Cart;
import com.wavemaker.ekisan.dto.Orders;
import com.wavemaker.ekisan.dto.Product;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrdersDaoImpl implements OrdersDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrdersDaoImpl.class);

	@Override
	public Orders findOrderByID(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = null;
		Orders orders = null;
		try {
			query = "select * from orders where id=? ";
			connection = DBConnection.getConnectionNonSingleTon();
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, id);

			ResultSet resultset = preparedStatement.executeQuery();
			orders = new Orders();
			while (resultset.next()) {
				orders.setId(resultset.getInt("id"));
				orders.setTotal_items(resultset.getInt("total_items"));
				orders.setTotal_amount(resultset.getFloat("total_amount"));
				orders.setDate(resultset.getDate("order_date"));
				orders.setDelivery_address_id(resultset.getInt("delivery_address_id"));
				orders.setUpdatedAt(resultset.getDate("updatedAt"));
				orders.setUpdatedBy(resultset.getInt("updatedBy"));
				orders.setSellerId(resultset.getInt("sellerId"));

			}

		} catch (DatabaseException databaseException) {
			LOGGER.error("Exception while creating Database Connection.", databaseException);
			// databaseException.printStackTrace();
			throw databaseException;
		} catch (SQLException exception) {
			LOGGER.error("SQLException occured while reading data from Database.", exception);
			throw new DatabaseException("Exception occured while reading data from Database.");
		} catch (Exception exception) {
			LOGGER.error("Exception occured while reading data from Database.", exception);
			throw new DatabaseException("Exception occured while reading data from Database.");
		} finally {
			DBConnection.closeConnection(connection);
		}
		return orders;
	}

	@Override
	public List<Orders> findAllOrders(int buyerId, int sellerId) {
		List<Orders> ordersList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Orders orders = null;
		try {
			connection = DBConnection.getConnectionNonSingleTon();
			if (buyerId != 0) {
				preparedStatement = connection
						.prepareStatement("SELECT o.* from orders o where o.updatedBy=" + buyerId);
			}
			if (sellerId != 0) {
				preparedStatement = connection
						.prepareStatement("SELECT o.* from orders o where o.sellerId=" + sellerId);
			}
			ResultSet resultset = preparedStatement.executeQuery();

			while (resultset.next()) {
				orders = new Orders();
				orders.setId(resultset.getInt("id"));
				orders.setTotal_items(resultset.getInt("total_items"));
				orders.setTotal_amount(resultset.getFloat("total_amount"));
				orders.setDate(resultset.getDate("order_date"));
				orders.setDelivery_address_id(resultset.getInt("delivery_address_id"));
				orders.setUpdatedAt(resultset.getDate("updatedAt"));
				orders.setUpdatedBy(resultset.getInt("updatedBy"));
				orders.setStatus(resultset.getString("status"));
				orders.setSellerId(resultset.getInt("sellerId"));

				ordersList.add(orders);
			}

		} catch (DatabaseException databaseException) {
			LOGGER.error("Exception while creating Database Connection.", databaseException);
			// databaseException.printStackTrace();
			throw databaseException;
		} catch (SQLException exception) {
			LOGGER.error("SQLException occured while reading data from Database.", exception);
			throw new DatabaseException("Exception occured while reading data from Database.");
		} catch (Exception exception) {
			LOGGER.error("Exception occured while reading data from Database.", exception);
			throw new DatabaseException("Exception occured while reading data from Database.");
		} finally {
			DBConnection.closeConnection(connection);
		}
		return ordersList;
	}

	public boolean saveOrUpdate(Orders orders) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBConnection.getConnectionNonSingleTon();
			String query = null;
			if (orders.getId() != 0) {
				query = "update orders set total_items= ?,total_amount =?,order_date = ?,delivery_address_id =?,updatedAt=?,updatedBy =?,status = ? where id= ?";
			} else {
				query = "insert into orders(total_items,total_amount,order_date,delivery_address_id,updatedAt,updatedBy,status) values(?,?,?,?,?,?,?)";
			}
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, orders.getTotal_items());
			preparedStatement.setDouble(2, orders.getTotal_amount());
			preparedStatement.setDate(3, (Date) orders.getDate());
			preparedStatement.setInt(4, orders.getDelivery_address_id());
			preparedStatement.setDate(5, (Date) orders.getUpdatedAt());
			preparedStatement.setInt(6, orders.getUpdatedBy());
			preparedStatement.setString(7, orders.getStatus());
			if (orders.getId() != 0) {
				preparedStatement.setInt(8, orders.getId());
			}

			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				return true;
			}

		} catch (DatabaseException databaseException) {
			LOGGER.error("Exception while saving data into Database Connection.", databaseException);
			// databaseException.printStackTrace();
			throw databaseException;
		} catch (SQLException exception) {
			LOGGER.error("SQLException occured while saving data into Database.", exception);
			throw new DatabaseException("Exception occured while inserting data into Database.");
		} catch (Exception exception) {
			LOGGER.error("Exception occured while inserting data into Database.", exception);
			throw new DatabaseException("Exception occured while inserting data into Database.");
		} finally {
			DBConnection.closeConnection(connection);
		}
		return false;
	}

	@Override
	public boolean checkOutOrder(int userId, Map<Integer, List<Cart>> cartMap, int addressId) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = null;
		List<Cart> cartList = null;
		try {
			connection = DBConnection.getConnectionNonSingleTon();
			connection.setAutoCommit(false);
			int orderId = 0;
			int rowsInserted = 0;
			for (Map.Entry<Integer, List<Cart>> cartEntry : cartMap.entrySet()) {
				cartList = cartEntry.getValue();
				for (int i = 0; i < cartList.size(); i++) {
					Cart cart = cartList.get(i);

					if (i == 0) {

						query = "insert into orders(total_items,total_amount,order_date,delivery_address_id,updatedAt,updatedBy,status, sellerId) values(?,?,?,?,?,?,?,?)";
						double totalAmount = cartList.stream().filter(a -> a != null).mapToDouble(Cart::getPrice).sum();
						preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
						preparedStatement.setInt(1, cartList.size());
						preparedStatement.setDouble(2, totalAmount);
						preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
						preparedStatement.setInt(4, addressId);
						preparedStatement.setDate(5, new Date(new java.util.Date().getTime()));
						preparedStatement.setInt(6, userId);
						preparedStatement.setString(7, "CREATED");
						preparedStatement.setInt(8, cart.getSellerId());
						rowsInserted = preparedStatement.executeUpdate();
						ResultSet rs = preparedStatement.getGeneratedKeys();
						while (rs.next())
							orderId = rs.getInt(1);

					}
					query = "insert into order_details(order_id,product_id,product_name, item_quantity,item_price,item_total,updatedBy,updatedAt) values(?,?,?,?,?,?,?,?)";
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setInt(1, orderId);
					preparedStatement.setInt(2, cart.getProductId());
					preparedStatement.setString(3, cart.getProductName());
					preparedStatement.setInt(4, cart.getQuantity());
					preparedStatement.setDouble(5, cart.getPrice());
					preparedStatement.setDouble(6, cart.getPrice() * cart.getQuantity());
					preparedStatement.setInt(7, userId);
					preparedStatement.setDate(8, new Date(new java.util.Date().getTime()));
					preparedStatement.executeUpdate();

				}
			}

			connection.commit();
			if (rowsInserted > 0)
				return true;

		} catch (DatabaseException databaseException) {
			connection.rollback();
			LOGGER.error("Exception while saving data into Database Connection.", databaseException);
			// databaseException.printStackTrace();
			throw databaseException;
		} catch (SQLException exception) {
			connection.rollback();
			LOGGER.error("SQLException occured while saving data into Database.", exception);
			throw new DatabaseException("Exception occured while inserting data into Database.");
		} catch (Exception exception) {
			connection.rollback();
			LOGGER.error("Exception occured while inserting data into Database.", exception);
			throw new DatabaseException("Exception occured while inserting data into Database.");
		} finally {
			DBConnection.closeConnection(connection);
		}
		return false;
	}
}
