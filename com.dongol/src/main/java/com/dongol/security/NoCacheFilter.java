package com.dongol.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCacheFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpRes = (HttpServletResponse) response;
            httpRes.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpRes.setHeader("Pragma", "no-cache");
            httpRes.setDateHeader("Expires", 0);
        }

        chain.doFilter(request, response);
    }
}