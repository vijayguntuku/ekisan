package com.wavemaker.ekisan.servlet;

import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.service.OrdersService;
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

@WebServlet(urlPatterns = {"/seller/listallorders","/buyer/listallorders"})
public class OrdersListServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersListServlet.class);

    @Inject
    OrdersService ordersService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;
        try {
            int buyerId = Integer.parseInt(req.getParameter("buyerId"));
            int sellerId = Integer.parseInt(req.getParameter("sellerId"));

            response = ordersService.findAllOrders(buyerId,sellerId);
            PrintWriter out = resp.getWriter();
            out.println(JsonUtils.convertToString(response));
        }catch (Exception e){
            String message = "OrdersListServlet:doGet() Exception occured while reading data from Database.";
            response = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }
}
