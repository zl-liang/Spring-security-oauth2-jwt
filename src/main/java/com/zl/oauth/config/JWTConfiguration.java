package com.zl.oauth.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.zl.oauth.service.UserDetailsForSecurityService;

@Configuration
public class JWTConfiguration {

  @Autowired
  private UserDetailsForSecurityService userDetailsService;

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(tokenEnhancer());
  }

  @Bean
  public TokenEnhancerChain tokenEnhancerChain() {
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    List<TokenEnhancer> tokenEnhancerList = new ArrayList<TokenEnhancer>();
    tokenEnhancerList.add(new JWTTokenEnhancer());
    tokenEnhancerList.add(tokenEnhancer());
    tokenEnhancerChain.setTokenEnhancers(tokenEnhancerList);
    return tokenEnhancerChain;
  }

  @Bean
  public JwtAccessTokenConverter tokenEnhancer() {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter());
    jwtAccessTokenConverter.setSigningKey("123456");
    return jwtAccessTokenConverter;
  }

  @Bean
  public DefaultAccessTokenConverter defaultAccessTokenConverter() {
    DefaultAccessTokenConverter converter = new DefaultAccessTokenConverter();
    DefaultUserAuthenticationConverter userConverter = new DefaultUserAuthenticationConverter();
    userConverter.setUserDetailsService(userDetailsService);
    converter.setUserTokenConverter(userConverter);
    return converter;
  }

}
