package com.kjsp.kjspslide.service;

import com.kjsp.kjspslide.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthService {

  private final UserInfoRepository userInfoRepository;


  public String loginUser() {

    return null;
  }

}
