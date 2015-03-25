package com.muhardin.endy.belajar.springoauth2.resourceserver;

import com.muhardin.endy.belajar.springoauth2.resourceserver.interceptor.CORSFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("classpath:clients.properties")
@ComponentScan
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    @Value("${allowedHosts}")
    private String allowedHosts;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("Allowed Host : "+allowedHosts);
        registry.addInterceptor(new CORSFilter(allowedHosts));
    }

}
