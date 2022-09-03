package com.wavemaker.ekisan.servlet;

import com.wavemaker.ekisan.dto.Cart;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.service.CartService;
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
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/buyer/checkout"})
public class CheckoutServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutServlet.class);

    @Inject
    CartService cartService;
    
    @Inject
    OrdersService orderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Response resp = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            resp = cartService.findAllCartItems(id);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "CheckoutServlet:doGet() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 Response resp = null;
         try {
        	 int id = Integer.parseInt(request.getParameter("userId"));
        	 int addressId = Integer.parseInt(request.getParameter("addressId"));
             HttpSession session=request.getSession();
             resp = orderService.checkOrder(id, addressId);
             PrintWriter out = response.getWriter();
             out.println(JsonUtils.convertToString(resp));
         }catch (Exception e){
             String message = "CartServlet:doPost() Exception occured while reading data from Database.";
             resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
         }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response resp = null;
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int productId = Integer.parseInt(request.getParameter("productId"));

            resp = cartService.deleteItemFromCart(userId, productId);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "CartServlet:doDelete() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }
}
