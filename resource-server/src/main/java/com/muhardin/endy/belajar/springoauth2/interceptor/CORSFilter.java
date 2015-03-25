package com.muhardin.endy.belajar.springoauth2.interceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CORSFilter extends HandlerInterceptorAdapter {

    private final String allowedHosts;

    public CORSFilter(String allowedHosts) {
        this.allowedHosts = allowedHosts;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(allowedHosts.split(",")));
        String origin = request.getHeader("Origin");
        if (allowedOrigins.contains(origin)) {
            System.out.println("Origin "+origin+" terdaftar dalam clients.properties");
            response.addHeader("Access-Control-Allow-Origin", origin);
            if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
                response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
                response.addHeader("Access-Control-Allow-Headers", "Content-Type");
                response.addHeader("Access-Control-Max-Age", "1");// 30 min
            }
            return true;
        } else {
            System.out.println("Origin " + origin + " belum didaftarkan di clients.properties");
            return true;
        }
    }
}
