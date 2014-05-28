package com.muhardin.endy.belajar.springoauth2.authserver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class DispatcherServletConfig implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();
        context.scan("com.muhardin.endy.belajar.springoauth2");
        
        sc.addListener(new ContextLoaderListener(context));
        
        sc.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"))
                    .addMappingForUrlPatterns(null, false, "/*");
        
        ServletRegistration.Dynamic registration = sc.addServlet("dispatcher", new DispatcherServlet(context));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
    
}
