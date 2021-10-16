package com.kjsp.kjspslide.controller;

import com.google.gson.Gson;
import com.kjsp.kjspslide.constant.MediaTypeConstant;
import com.kjsp.kjspslide.entity.UserInfo;
import com.kjsp.kjspslide.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/user", produces = MediaTypeConstant.APPLICATION_REST_JSON_VALUE)
public class UserInfoController {
  private final UserInfoRepository userInfoRepository;

  @Autowired
  public UserInfoController(
    UserInfoRepository userInfoRepository
  ) {
    this.userInfoRepository = userInfoRepository;
  }

  // TODO
  @PostMapping(value = "/signin")
  public String signIn(
      @RequestBody String body
  ) {

    return null;
  }

  // TODO
  @PostMapping(value = "/signout")
  public String signOut() {
    return null;
  }

  // TODO
  @PostMapping(value = "/signup")
  public String signUp(
      @RequestBody String body // "{"name":"John", "age":"20"}"
  ) {
    Gson gson = new Gson(); // Gson 객체 생성
    UserInfo userInfo = gson.fromJson(body, UserInfo.class);
    return null;
  }
}
