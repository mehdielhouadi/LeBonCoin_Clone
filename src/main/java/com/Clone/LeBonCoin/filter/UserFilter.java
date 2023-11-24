package com.Clone.LeBonCoin.filter;

import com.Clone.LeBonCoin.Entity.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Order(2)
public class UserFilter implements Filter{

    @Autowired
    SecurityUtils securityUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("AUTHORIZATION");

        if ( !securityUtil.isValidToken(token) || !securityUtil.canAccess(token, "USER")) {
            throw new ServletException();
        }

        try {
            request.setAttribute("email", securityUtil.getSubject(securityUtil.getTokenFromBearerToken(token)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("THIS IS USER ROUTE");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
