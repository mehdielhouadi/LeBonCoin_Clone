package com.Clone.LeBonCoin.filter;

import com.Clone.LeBonCoin.Entity.SecurityUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.io.IOException;

@Order(1)
public class AdminFilter implements Filter {
    @Autowired
    SecurityUtils securityUtils;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("AUTHORIZATION");

        if (  !securityUtils.isValidToken(token) || !securityUtils.canAccess(token, "ADMIN") ) {
            throw new ServletException();
        }

        filterChain.doFilter(request, response);
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
