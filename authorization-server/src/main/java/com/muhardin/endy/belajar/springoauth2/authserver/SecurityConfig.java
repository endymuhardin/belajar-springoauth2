package com.muhardin.endy.belajar.springoauth2.authserver;

import com.muhardin.endy.belajar.springoauth2.authserver.handler.Oauth2LogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private Oauth2LogoutHandler logoutHandler;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("endy").password("123").roles("ADMIN").and()
                .withUser("anton").password("123").roles("STAFF");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
        
        http.authorizeRequests()
                .antMatchers("/login.jsp").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login.jsp")
                    .loginProcessingUrl("/j_spring_security_check")
                    .usernameParameter("j_username")
                    .passwordParameter("j_password")
                .and()
                    .logout()
                    .logoutUrl("/j_spring_security_logout")
                    .logoutSuccessHandler(logoutHandler);
    }
    
    
}
