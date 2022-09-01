package com.wavemaker.ekisan.servlet;

import com.wavemaker.ekisan.dto.ErrorHolder;
import com.wavemaker.ekisan.dto.Product;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.request.LoginRequest;
import com.wavemaker.ekisan.service.ProductService;
import com.wavemaker.ekisan.service.ProductServiceImpl;
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

@WebServlet(urlPatterns ={"/seller/products","/buyer/products","/admin/products"} )
public class ProductServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServlet.class);
    @Inject
    ProductService productService;

    @Inject
    ProductValidator validator;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response resp = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            resp = productService.findProduct(id, DBMasterConstants.PRODUCT_STATUS_APPROVED);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "ProductServlet:doGet() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Response resp = null;
        PrintWriter out = response.getWriter();
        try {

            Product product = JsonUtils.convertToObject(req, Product.class);
           ErrorHolder errorHolder =  validator.validate(new ErrorHolder(), product);
           if(errorHolder.hasErrors()){
               resp = ResponseUtils.createResponse(false, "Bad Request.", 400,errorHolder.getErrorList());
               response.setStatus(400);
               out.println(JsonUtils.convertToString(resp));
           }else {
               resp = productService.saveOrUpdate(product);
               out.println(JsonUtils.convertToString(resp));
           }
        }catch (Exception e){
            String message = "LoginServlet:doPost() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
            response.setStatus(500);
            out.println(JsonUtils.convertToString(resp));
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Response resp = null;
        PrintWriter out = response.getWriter();
        try {

            Product product = JsonUtils.convertToObject(req, Product.class);
            ErrorHolder errorHolder =  validator.validate(new ErrorHolder(), product);
            if(errorHolder.hasErrors()){
                resp = ResponseUtils.createResponse(false, "Bad Request.", 400,errorHolder.getErrorList());
                response.setStatus(400);
                out.println(JsonUtils.convertToString(resp));
            }else {
                resp = productService.saveOrUpdate(product);
                out.println(JsonUtils.convertToString(resp));
            }
        }catch (Exception e){
            String message = "LoginServlet:doPost() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
            response.setStatus(500);
            out.println(JsonUtils.convertToString(resp));
        }
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response resp = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            resp = productService.deleteProduct(id);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "ProductServlet:doGet() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }
}
