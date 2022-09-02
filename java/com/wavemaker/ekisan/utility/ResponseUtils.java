package com.wavemaker.ekisan.utility;

import com.wavemaker.ekisan.dto.ErrorHolder;
import com.wavemaker.ekisan.dto.Response;
import org.slf4j.Logger;

public class ResponseUtils {
    public static Response createInternalServlerErrorResponse(Logger logger,Exception e,String message,  Object ...args) {
       Response resp = new Response();
        resp.setSuccess(false);
        resp.setMessage(Constants.INTERNAL_SERVER_ERROR_MESSAGE);
        resp.setStatusCode(500);
        logger.error(message, e, args);
        return resp;
    }

    public static <T> Response createResponse(boolean success, String message, int statusCode, T data) {
        Response resp = new Response();
        resp.setSuccess(success);
        resp.setMessage(message);
        resp.setStatusCode(statusCode);
        resp.setData(data);
        return resp;
    }

}
