package com.wavemaker.ekisan.servlet;

import com.wavemaker.ekisan.dao.ProductDaoImpl;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.service.ProductService;
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

@WebServlet("/seller/productlist")
public class ProductListServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductListServlet.class);
    @Inject
    ProductService productService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;
        try {

            response = productService.findAllProducts();
            PrintWriter out = resp.getWriter();
            out.println(JsonUtils.convertToString(response));
        }catch (Exception e){
            String message = "ProductListServlet:doGet() Exception occured while reading data from Database.";
            response = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }
}
