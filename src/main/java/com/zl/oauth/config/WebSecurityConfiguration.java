package com.zl.oauth.config;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.zl.oauth.filter.UserPasswordEncryptionFilter;
import com.zl.oauth.service.UserDetailsForSecurityService;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsForSecurityService userDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        HttpSession session = UserPasswordEncryptionFilter.CONCURRECT_SESSION.get();
        String password = "";
        if (null != session) {
          password = (String) session.getAttribute("password");
        } else {
          password = (String) rawPassword;
        }
        return encodedPassword.equals(password);
      }

      @Override
      public String encode(CharSequence rawPassword) {
        return (String) rawPassword;
      }
    });
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests().antMatchers("/**").permitAll().and().formLogin().disable()
        .authorizeRequests().anyRequest().authenticated();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
