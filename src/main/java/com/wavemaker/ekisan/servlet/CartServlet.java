package com.wavemaker.ekisan.servlet;

import com.wavemaker.ekisan.dto.Address;
import com.wavemaker.ekisan.dto.Cart;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.dto.User;
import com.wavemaker.ekisan.service.CartService;
import com.wavemaker.ekisan.utility.Constants;
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

@WebServlet(urlPatterns = "/buyer/cart")

public class CartServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServlet.class);

    @Inject
    CartService cartService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response resp = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            resp = cartService.findCartById(id);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "CartServlet:doGet() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Response resp = null;
        try {
            Cart cart =JsonUtils.convertToObject(req, Cart.class);
            HttpSession session=req.getSession();
            //User user = (User)session.getAttribute(Constants.SESSION_USER);
            //cart.setUserId(user.getId());
            resp = cartService.saveOrUpdateCart(cart);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "CartServlet:doPost() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response resp = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            resp = cartService.deleteCart(id);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "CartServlet:doDelete() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }

}
