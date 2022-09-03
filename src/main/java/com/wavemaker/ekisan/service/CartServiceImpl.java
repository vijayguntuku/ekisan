package com.wavemaker.ekisan.service;

import com.wavemaker.ekisan.dao.CartDao;
import com.wavemaker.ekisan.dto.Cart;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class CartServiceImpl implements CartService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Inject
    CartDao cartDao;

    @Override
    public Response findCartById(int id) {
        Response resp = null;
        try {
            List<Cart> itemList=cartDao.findCartByID(id);
            if(itemList.size()>0) {
                resp = ResponseUtils.createResponse(true, "Order Retrieved successfully from cart table with id="+id,200,itemList);
            }
            else {
                resp = ResponseUtils.createResponse(true, "No Items found with given user id=."+id,200,null);
            }
        }catch (DatabaseException e){
            String message = "CartServiceImpl:findCartId(id) Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="CartServiceImpl:findCartId(id) Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }

    @Override
    public Response saveOrUpdateCart(Cart cart) {
        Response resp = null;
        try {
            boolean inserted = cartDao.saveOrUpdateCart(cart);

            if(inserted)
            {
                List<Cart> itemsList = cartDao.findCartByID(cart.getUserId());
                resp = ResponseUtils.createResponse(true, "Data saved successfully",200,itemsList);
            }

        }catch (DatabaseException e){
            String message = "CartServiceImpl:saveOrUpdate Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="CartServiceImpl:saveOrUpdate  Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }

    @Override
    public Response deleteCart(int id) {
        Response resp = null;
        try {
            boolean isDeleted = cartDao.deleteCart(id);
            if(isDeleted) {
                resp = ResponseUtils.createResponse(true, "Cart deleted successfully with given id="+id,200,null);
            }
        }catch (DatabaseException e){
            String message = "cartServiceImpl:deleteCart() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="CARTServiceImpl:deleteCart() Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }
}
