package com.zl.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import com.zl.oauth.service.UserDetailsForSecurityService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  public static final String WEB_SERVER = "web_server";

  public static final String WEB_SERVER_PASSWORD =
      "jhi923d-ae3dsa-12asda-ad3ea-sdwq-asdarwe-zsswr3";

  private static final int REFRESH_TIME_SECS = 3600;

  @Autowired
  private JWTConfiguration jwtConfiguration;

  @Autowired
  private ClientDetailsService clientDetailsService;

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsForSecurityService userDetailsService;

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer.checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer)
      throws Exception {
    endpointsConfigurer.tokenServices(defaultTokenServices())
        .authenticationManager(this.authenticationManager)
        .accessTokenConverter(new UserAccessTokenConverter())
        .userDetailsService(userDetailsService);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory().withClient(WEB_SERVER).authorizedGrantTypes("password", "refreh_token")
        .authorities("USER").scopes("read", "write").secret(WEB_SERVER_PASSWORD);
  }

  @Bean
  public DefaultTokenServices defaultTokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(jwtConfiguration.tokenStore());
    defaultTokenServices.setClientDetailsService(clientDetailsService);
    defaultTokenServices.setTokenEnhancer(jwtConfiguration.tokenEnhancerChain());
    defaultTokenServices.setSupportRefreshToken(true);
    defaultTokenServices.setRefreshTokenValiditySeconds(REFRESH_TIME_SECS);
    return defaultTokenServices;
  }

}
