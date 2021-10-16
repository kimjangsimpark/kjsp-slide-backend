package com.kjsp.kjspslide.controller;

import com.kjsp.kjspslide.dto.JwtDto;
import com.kjsp.kjspslide.dto.UserDto;
import com.kjsp.kjspslide.dto.UserDto.Request;
import com.kjsp.kjspslide.service.UserAuthService;
import com.kjsp.kjspslide.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/user")
public class UserInfoController {

  private final UserAuthService userAuthService;
  private final UserService userService;

  @Autowired
  public UserInfoController(
      UserAuthService userAuthService,
      UserService userService
  ) {
    this.userAuthService = userAuthService;
    this.userService = userService;
  }

  @PostMapping(value = "/info")
  public ResponseEntity<UserDto.Response> getuserInfo(
      HttpServletRequest httpServletRequest
  ) {
    UserDto.Response responseDto = userService.getUserInfo((String) httpServletRequest.getAttribute("subject"));
    if (responseDto != null) {
      return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PostMapping(value = "/signin")
  public ResponseEntity<JwtDto.Response> signIn(
      @RequestBody UserDto.Request userDto
  ) {
    boolean isSuccess = userService.validateUserPassword(userDto);
    if (isSuccess) {
      String token = userAuthService.createToken(userDto);
      return new ResponseEntity<>(JwtDto.Response.builder().accessToken(token).tokenType("Bearer").build(), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PostMapping(value = "/signup")
  public ResponseEntity<Object> signUp(
      @RequestBody Request userDto // "{"userName":"수영", "userPassword":"test1234"}"
  ) {
    return new ResponseEntity<>(userService.saveUserInfo(userDto));
  }
}
