package com.muhardin.endy.belajar.springoauth2.authserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class Oauth2AuthorizationServer {

    private static final String RESOURCE_ID = "belajar";

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends
            AuthorizationServerConfigurerAdapter {

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                    .tokenStore(new InMemoryTokenStore())
                    .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.checkTokenAccess("hasRole('CLIENT')");
        }
        
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient("clientauthcode")
                    .resourceIds(RESOURCE_ID)
                    .authorizedGrantTypes("authorization_code")
                    .secret("123456")
                    .scopes("read", "write")
                    .and()
                    .withClient("clientapp")
                    .authorizedGrantTypes("password")
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                    .secret("123456")
                    .and()
                    .withClient("jsclient")
                    .secret("jspasswd")
                    .authorizedGrantTypes("implicit")
                    .authorities("CLIENT")
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                    .redirectUris("http://localhost:10001/resource-server/api/state/verify")
                    .accessTokenValiditySeconds(60 * 60 * 24) // token berlaku seharian, besok harus login ulang
                    .autoApprove(true);
        }
    }
}
