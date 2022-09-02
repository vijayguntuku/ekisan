package com.wavemaker.ekisan.service;

import com.wavemaker.ekisan.dao.OrdersDao;
import com.wavemaker.ekisan.dto.Orders;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;




public class OrdersServiceImpl implements OrdersService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Inject
    OrdersDao ordersDao;

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
    public Response findAllOrders() {
        Response resp = null;
        try {
            resp = ResponseUtils.createResponse(true, "Product Retrieved successfully=",200,ordersDao.findAllOrders());
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

}
