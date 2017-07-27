package com.zl.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zl.oauth.model.TbUser;

public interface TbUserRepository extends JpaRepository<TbUser, Long> {

  public TbUser findByUserName(String userName);

}
