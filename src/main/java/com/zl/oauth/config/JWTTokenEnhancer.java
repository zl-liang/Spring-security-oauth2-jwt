package com.zl.oauth.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.zl.oauth.model.UserLoginInfo;

@Configuration
public class JWTTokenEnhancer implements TokenEnhancer {

  private static final int TIMEOUT_SECS = 3000;

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    UserLoginInfo userLoginInfo = (UserLoginInfo) authentication.getPrincipal();

    Map<String, Object> additionalInfo = new HashMap<String, Object>();
    additionalInfo.put("userId", userLoginInfo.getId().toString());
    additionalInfo.put("exp", new Date().getTime() / 1000 + TIMEOUT_SECS);
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    return accessToken;
  }

}
