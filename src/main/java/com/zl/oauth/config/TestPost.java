package com.zl.oauth.config;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestPost implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public String testValue = "";

  public static void main(String[] args) {
    try {
      URL url = new URL(
          "http://localhost:10010/oauth/token?password=1234&username=1234&grant_type=password");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      String input = "web_server:jhi923d-ae3dsa-12asda-ad3ea-sdwq-asdarwe-zsswr3";
      conn.setRequestProperty("Authorization",
          "Basic " + new String(Base64.encodeBase64(input.getBytes()), "utf-8"));
      if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
        InputStream in = conn.getInputStream();
        String token = IOUtils.toString(in);
        System.out.println("token: " + token);
        @SuppressWarnings("unchecked")
        Map<String, String> tokenMap = new ObjectMapper().readValue(token, Map.class);
        System.out.println("access_token: " + tokenMap.get("access_token"));
      } else {
        System.out.println("responese: " + conn.getResponseMessage());
      }
      conn.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
