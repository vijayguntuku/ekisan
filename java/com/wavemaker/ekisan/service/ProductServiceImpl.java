package com.wavemaker.ekisan.service;

import com.wavemaker.ekisan.dao.ProductDao;
import com.wavemaker.ekisan.dto.Product;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.dto.User;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.Constants;
import com.wavemaker.ekisan.utility.DBMasterConstants;
import com.wavemaker.ekisan.utility.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class ProductServiceImpl implements ProductService{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Inject
    ProductDao productDao;

    @Override
    public Response findProduct(int id, String status) {
        Response resp = null;
        try {
            Product product = productDao.findProductByID(id, status);
            if(product!=null) {
                resp = ResponseUtils.createResponse(true, "Product Retrieved successfully with id="+id,200,product);
            }
            else {
                resp = ResponseUtils.createResponse(true, "No product found with given id=."+id,200,null);
            }
        }catch (DatabaseException e){
            String message = "ProductServiceImpl:findProduct() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="ProductServiceImpl:findProduct() Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }

    @Override
    public Response findAllProducts() {
        Response resp = null;
        try {
               resp = ResponseUtils.createResponse(true, "Product Retrieved successfully=",200,productDao.findAllProducts());
           }catch (DatabaseException e){
            String message = "ProductServiceImpl:findProduct() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="ProductServiceImpl:findProduct() Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }

    @Override
    public Response saveOrUpdate(Product product) {
        Response resp = null;
        try {
           boolean inserted = productDao.saveOrUpdate(product);
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
    public Response deleteProduct(int id) {
        Response resp = null;
        try {
            boolean isDeleted = productDao.deleteProduct(id);
            if(isDeleted) {
                resp = ResponseUtils.createResponse(true, "Product deleted successfully with given id="+id,200,null);
            }
        }catch (DatabaseException e){
            String message = "ProductServiceImpl:deleteProduct() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="ProductServiceImpl:deleteProduct() Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }

}
