package com.wavemaker.ekisan.servlet;

import com.wavemaker.ekisan.dto.Orders;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.dto.User;
import com.wavemaker.ekisan.service.UserService;
import com.wavemaker.ekisan.utility.JsonUtils;
import com.wavemaker.ekisan.utility.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/admin/users","/buyer/users","/seller/users"})
public class UserServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);

    @Inject
    UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response resp = null;
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));

            resp = userService.findUserById(userId);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "UsersServlet:doGet() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Response resp = null;
        try {
            User user = JsonUtils.convertToObject(req, User.class);
            resp = userService.saveOrUpdateUser(user);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));

        }catch (Exception e){
            String message = "UserServlet():doPost() Exception occured while updating data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }

}
