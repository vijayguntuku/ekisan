package com.wavemaker.ekisan.servlet;

import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.request.LoginRequest;
import com.wavemaker.ekisan.service.UserService;
import com.wavemaker.ekisan.utility.ResponseUtils;
import com.wavemaker.ekisan.utility.JsonUtils;
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

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet  {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    @Inject
    UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Do GET Method");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Response resp = null;
       try {
           LoginRequest loginRequest = JsonUtils.convertToObject(request, LoginRequest.class);
           resp = userService.login(loginRequest, request.getSession());
           PrintWriter out = response.getWriter();
           out.println(JsonUtils.convertToString(resp));
       }catch (Exception e){
           String message = "LoginServlet:doPost() Exception occured while reading data from Database.";
           resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
       }
    }
}
