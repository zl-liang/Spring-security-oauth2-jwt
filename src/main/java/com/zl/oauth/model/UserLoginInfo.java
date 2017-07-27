package com.zl.oauth.model;


public class UserLoginInfo {

  private Long id;

  private String username;

  private String password;

  public UserLoginInfo() {}

  public UserLoginInfo(UserLoginInfo userLoginInfo) {
    this.id = userLoginInfo.id;
    this.username = userLoginInfo.username;
    this.password = userLoginInfo.password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
