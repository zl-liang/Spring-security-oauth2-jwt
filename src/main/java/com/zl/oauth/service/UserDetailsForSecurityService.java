package com.zl.oauth.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zl.oauth.model.TbUser;
import com.zl.oauth.model.UserLoginInfo;
import com.zl.oauth.repository.TbUserRepository;

@Service
public class UserDetailsForSecurityService implements UserDetailsService {

  @Autowired
  private TbUserRepository tbUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    TbUser tbUser = tbUserRepository.findByUserName(username);
    if (null == tbUser) {
      throw new UsernameNotFoundException("User Not Exists!!!");
    }
    UserLoginInfo userLoginInfo = new UserLoginInfo();
    userLoginInfo.setId(tbUser.getId());
    userLoginInfo.setPassword(tbUser.getPassword());
    userLoginInfo.setUsername(tbUser.getUserName());


    // TODO Auto-generated method stub
    return new UserRepositoryUserDetails(userLoginInfo);
  }

  private final static class UserRepositoryUserDetails extends UserLoginInfo
      implements
        UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = 380403777703133444L;

    public UserRepositoryUserDetails(UserLoginInfo userLoginInfo) {
      super(userLoginInfo);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      // TODO Auto-generated method stub
      return AuthorityUtils.commaSeparatedStringToAuthorityList("USER");
    }

    @Override
    public boolean isAccountNonExpired() {
      // TODO Auto-generated method stub
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      // TODO Auto-generated method stub
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      // TODO Auto-generated method stub
      return true;
    }

    @Override
    public boolean isEnabled() {
      // TODO Auto-generated method stub
      return true;
    }

  }
}
