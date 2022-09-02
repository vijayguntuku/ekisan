package com.wavemaker.ekisan.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.service.ProductCategoryService;
import com.wavemaker.ekisan.utility.JsonUtils;
import com.wavemaker.ekisan.utility.ResponseUtils;

@WebServlet(urlPatterns ={"/seller/categories","/buyer/categories","/admin/categories"} )
public class ProductCategoryServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServlet.class);
    @Inject
    ProductCategoryService productCategoryService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response resp = null;
        try {

            resp = productCategoryService.findAllProductCategories();
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "ProductCategoryServlet:doGet() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }

}
