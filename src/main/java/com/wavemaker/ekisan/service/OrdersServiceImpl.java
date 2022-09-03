package com.wavemaker.ekisan.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.ekisan.dao.CartDao;
import com.wavemaker.ekisan.dao.OrdersDao;
import com.wavemaker.ekisan.dto.Cart;
import com.wavemaker.ekisan.dto.Orders;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.ResponseUtils;


public class OrdersServiceImpl implements OrdersService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Inject
    OrdersDao ordersDao;
    
    @Inject
    CartDao cartDao;
    

    @Override
    public Response findOrderById(int id) {
        Response resp = null;
        try {
            Orders order = ordersDao.findOrderByID(id);
            if(order!=null) {
                resp = ResponseUtils.createResponse(true, "Order Retrieved successfully with id="+id,200,order);
            }
            else {
                resp = ResponseUtils.createResponse(true, "No Order found with given id=."+id,200,null);
            }
        }catch (DatabaseException e){
            String message = "OrdersServiceImpl:findOrderByID(id) Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="OrdersServiceImpl:findOrderByID(id) Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }
    @Override
    public Response findAllOrders(int buyerId,int sellerId) {
        Response resp = null;
        try {

            List<Orders> order = ordersDao.findAllOrders(buyerId,sellerId);

            resp = ResponseUtils.createResponse(true, "Product Retrieved successfully=",200,order);
        }catch (DatabaseException e){
            String message = "OrdersServiceImpl:findAllOrders() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="OrdersServiceImpl:findAllOrders() Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }

    @Override
    public Response saveOrUpdate(Orders orders) {
        Response resp = null;
        try {
            boolean inserted = ordersDao.saveOrUpdate(orders);
            if(inserted)
            {
                resp = ResponseUtils.createResponse(true, "Data saved successfully",200,null);
            }

        }catch (DatabaseException e){
            String message = "ProductServiceImpl:insert() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="ProductServiceImpl:insert() Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }
	@Override
	public Response checkOrder(int id, int addressId) {
		Response resp = null;
        try {
        	Map<Integer, List<Cart>> cartMap = cartDao.findAllCartItems(id);
            boolean inserted = ordersDao.checkOutOrder(id,cartMap, addressId);
            boolean deleted = cartDao.deleteCart(id);
            if(inserted)
            {
                resp = ResponseUtils.createResponse(true, "Order placed successfully.. Sit back and relax, your order will be delivered in a week..",200,null);
            }

        }catch (DatabaseException e){
            String message = "ProductServiceImpl:insert() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="ProductServiceImpl:insert() Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
	}

}
