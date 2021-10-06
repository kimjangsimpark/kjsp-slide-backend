package com.kjsp.kjspslide.controller;

import com.kjsp.kjspslide.constant.MediaTypeConstant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/user", produces = MediaTypeConstant.APPLICATION_REST_JSON_VALUE)
public class UserInfoController {

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
      @RequestBody String body
  ) {
    return null;
  }
}
