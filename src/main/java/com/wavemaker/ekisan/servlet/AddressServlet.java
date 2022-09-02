package com.wavemaker.ekisan.servlet;

import com.wavemaker.ekisan.dto.Address;
import com.wavemaker.ekisan.dto.ErrorHolder;
import com.wavemaker.ekisan.dto.Product;
import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.service.AddressService;
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

@WebServlet(urlPatterns = {"/seller/address","/buyer/address","/admin/address"})
public class AddressServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressServlet.class);

   @Inject
    AddressService addressService;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response resp = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            resp = addressService.findAddressById(id);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "AddressServlet:doGet() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Response resp = null;
        try {
            Address address =JsonUtils.convertToObject(req, Address.class);
            resp = addressService.saveOrUpdate(address);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "AddressServlet:doPost() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Response resp = null;

        try {
            Address address =JsonUtils.convertToObject(req, Address.class);
            resp = addressService.saveOrUpdate(address);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));

        }catch (Exception e){
            String message = "AddressServlet:doPut Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response resp = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            resp = addressService.deleteAddress(id);
            PrintWriter out = response.getWriter();
            out.println(JsonUtils.convertToString(resp));
        }catch (Exception e){
            String message = "AddressServlet:doDelete() Exception occured while reading data from Database.";
            resp = ResponseUtils.createInternalServlerErrorResponse(LOGGER, e, message);
        }
    }

}
