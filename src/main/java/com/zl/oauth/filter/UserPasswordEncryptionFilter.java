package com.zl.oauth.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class UserPasswordEncryptionFilter implements Filter {

  public static final ThreadLocal<HttpSession> CONCURRECT_SESSION = new ThreadLocal<HttpSession>();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // TODO Auto-generated method stub

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // TODO Auto-generated method stub
    HttpSession session = ((HttpServletRequest) request).getSession();
    if (((HttpServletRequest) request).getServletPath().contains("/oauth/token")) {
      String enyPassword = request.getParameter("password");
      // String account = request.getParameter("username");
      // 秘钥重新加密TODO
      session.setAttribute("password", enyPassword);
    }

    try {
      CONCURRECT_SESSION.set(session);;
      chain.doFilter(request, response);
    } catch (Exception e) {
      CONCURRECT_SESSION.remove();
    }

  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }

}
