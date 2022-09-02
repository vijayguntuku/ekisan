package com.wavemaker.ekisan.servlet;

import com.wavemaker.ekisan.dto.*;
import com.wavemaker.ekisan.service.OrdersService;
import com.wavemaker.ekisan.utility.DBMasterConstants;
import com.wavemaker.ekisan.utility.JsonUtils;
import com.wavemaker.ekisan.utility.ResponseUtils;
import com.wavemaker.ekisan.validator.ProductValidator;
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

@WebServlet(urlPatterns ={"/seller/orders","/buyer/orders","/admin/orders"})
public class OrdersServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersServlet.class);

    @Inject
    OrdersService ordersService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response resp = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            resp = ordersService.findOrderById(id);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "OrdersServlet:doGet() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Response resp = null;
        try {
            Orders orders =JsonUtils.convertToObject(req, Orders.class);
            resp = ordersService.saveOrUpdate(orders);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));

        }catch (Exception e){
            String message = "OrdersServlet():doPost() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }
}
