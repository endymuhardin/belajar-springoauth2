package com.muhardin.endy.belajar.springoauth2.authserver.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class Oauth2LogoutHandler implements LogoutSuccessHandler {

    @Autowired private ConsumerTokenServices consumerTokenServices;
    
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res, Authentication a) throws IOException, ServletException {
        String token=req.getParameter("token");
        if(token != null){
            consumerTokenServices.revokeToken(token);
        }
        res.addHeader("Access-Control-Allow-Origin", "*");
    }
}
