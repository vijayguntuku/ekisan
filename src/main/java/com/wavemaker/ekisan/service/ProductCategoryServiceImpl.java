package com.wavemaker.ekisan.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.ekisan.dao.ProductCategoryDao;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.ResponseUtils;

public class ProductCategoryServiceImpl implements ProductCategoryService{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
    @Inject
    ProductCategoryDao productCategoryDao;

    @Override
    public Response findAllProductCategories() {
        Response resp = null;
        try {
               resp = ResponseUtils.createResponse(true, "Product categories retrieved successfully=",200,productCategoryDao.findAllProductCategories());
           }catch (DatabaseException e){
            String message = "ProductCategoryServiceImpl:findAllProductCategories() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="ProductCategoryServiceImpl:findAllProductCategories() Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }
}
