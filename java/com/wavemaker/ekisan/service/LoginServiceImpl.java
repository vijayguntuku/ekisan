package com.wavemaker.ekisan.service;

import com.wavemaker.ekisan.dao.UserDao;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.dto.User;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.request.LoginRequest;
import com.wavemaker.ekisan.utility.Constants;
import com.wavemaker.ekisan.utility.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

public class LoginServiceImpl implements LoginService{

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Inject
    UserDao userDao;

    @Override
    public Response login(LoginRequest loginRequest, HttpSession session) {
        Response resp = null;
        try {
            User user = userDao.findUser(loginRequest.getEmail(), loginRequest.getPassword());
            if(user!=null) {
                resp = ResponseUtils.createResponse(true, "Loggedin Successfully",200,user);
                session.setAttribute(Constants.SESSION_USER,user);
            }
            else {
                resp = ResponseUtils.createResponse(true, "Username/Password incorrect.",200,null);
            }
        }catch (DatabaseException e){
            String message = "LoginServiceImpl:login() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }catch (Exception e){
            String message ="LoginServiceImpl:login() Exception occured while logging in to the application.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER,e, message);
        }

        return resp;
    }
}
