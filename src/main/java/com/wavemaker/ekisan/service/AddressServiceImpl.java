package com.wavemaker.ekisan.service;

import com.wavemaker.ekisan.dao.AddressDao;
import com.wavemaker.ekisan.dto.Address;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class AddressServiceImpl implements AddressService{
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Inject
    AddressDao addressDao;

    @Override
    public Response findAddressById(int id) {
        Response resp = null;
        try {
            Address address = addressDao.findAddressByID(id);
            if(address!=null) {
                resp = ResponseUtils.createResponse(true, "Order Retrieved successfully with id="+id,200,address);
            }
            else {
                resp = ResponseUtils.createResponse(true, "No Order found with given id=."+id,200,null);
            }
        }catch (DatabaseException e){
            String message = "AAddressServiceImpl:findAdreessId(id) Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="AddressServiceImpl:findAdreessId(id) Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }

    @Override
    public Response saveOrUpdate(Address address) {
        Response resp = null;
        try {
            boolean inserted = addressDao.saveOrUpdate(address);
            if(inserted)
            {
                resp = ResponseUtils.createResponse(true, "Data saved successfully",200,null);
            }

        }catch (DatabaseException e){
            String message = "AddressServiceImpl:saveOrUpdate Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="ddressServiceImpl:saveOrUpdate  Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }

    @Override
    public Response deleteAddress(int id) {
        Response resp = null;
        try {
            boolean isDeleted = addressDao.deleteAddress(id);
            if(isDeleted) {
                resp = ResponseUtils.createResponse(true, "Product deleted successfully with given id="+id,200,null);
            }
        }catch (DatabaseException e){
            String message = "addressServiceImpl:deleteAddress() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="addressServiceImpl:deleteAddress() Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }
}
