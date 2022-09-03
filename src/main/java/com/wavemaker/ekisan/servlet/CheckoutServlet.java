package com.wavemaker.ekisan.servlet;

import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.service.CartService;
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

@WebServlet(urlPatterns = {"/buyer/checkout"})
public class CheckoutServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutServlet.class);

    @Inject
    CartService cartService;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
