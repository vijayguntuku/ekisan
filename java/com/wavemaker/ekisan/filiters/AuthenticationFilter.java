package com.wavemaker.ekisan.filiters;

import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.dto.User;
import com.wavemaker.ekisan.utility.Constants;
import com.wavemaker.ekisan.utility.DBMasterConstants;
import com.wavemaker.ekisan.utility.JsonUtils;
import com.wavemaker.ekisan.utility.ResponseUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class AuthenticationFilter implements Filter {



    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
        HttpSession session=httpServletRequest.getSession();
        User user = (User)session.getAttribute(Constants.SESSION_USER);
        String uri = httpServletRequest.getRequestURI();
        PrintWriter out = httpServletResponse.getWriter();
            if(uri.contains("login")){
                filterChain.doFilter(servletRequest , servletResponse);
            }else if(user!=null &&
                ((uri.contains("admin") && DBMasterConstants.ROLE_ADMIN.equals(user.getRole()))
                || (uri.contains("buyer") && DBMasterConstants.ROLE_BUYER.equals(user.getRole()))
                || (uri.contains("seller") && DBMasterConstants.ROLE_SELLER.equals(user.getRole())))){
            filterChain.doFilter(servletRequest , servletResponse);
        } else if(null== user){
            Response resp = ResponseUtils.createResponse(false,"Login Required", 401,null);
            httpServletResponse.setStatus(401);
            out.println(JsonUtils.convertToString(resp));
        } else {
            Response resp = ResponseUtils.createResponse(false,"You dont have access to this resouces..", 403,null);
            httpServletResponse.setStatus(403);
            out.println(JsonUtils.convertToString(resp));
        }


    }
    public static String getCurrentUrlFromRequest(ServletRequest request)
    {
        if (! (request instanceof HttpServletRequest))
            return null;

        return getCurrentUrlFromRequest((HttpServletRequest)request);
    }

    public static String getCurrentUrlFromRequest(HttpServletRequest request)
    {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null)
            return requestURL.toString();

        return requestURL.append('?').append(queryString).toString();
    }
}
