package com.muhardin.endy.belajar.springoauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class Oauth2AuthorizationServer {

    private static final String RESOURCE_ID = "belajar";

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends
            AuthorizationServerConfigurerAdapter {

        private TokenStore tokenStore = new InMemoryTokenStore();

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                    .tokenStore(tokenStore)
                    .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient("clientapp")
                    .authorizedGrantTypes("password")
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                    .secret("123456")
                    .and()
                    .withClient("jsclient")
                    .authorizedGrantTypes("implicit")
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                    .redirectUris("http://localhost:8080/belajar-spring-oauth2/api/state/verify")
                    .accessTokenValiditySeconds(60 * 60 * 24) // token berlaku seharian, besok harus login ulang
                    .autoApprove(true);
        }

    }
    
    @Configuration @Order(10)
    protected static class NonOauthResources extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/api/halo").permitAll()
                    .antMatchers("/api/state/**").permitAll()
                    .and().anonymous();
        }
        
    }

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends
            ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources
                    .resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/api/staff").hasRole("STAFF")
                    .antMatchers("/api/admin").hasRole("ADMIN")
                    .and().formLogin();
        }

    }

}
