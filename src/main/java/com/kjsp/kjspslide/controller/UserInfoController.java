package com.kjsp.kjspslide.controller;

import com.kjsp.kjspslide.constant.JwtConstant;
import com.kjsp.kjspslide.dto.JwtDto;
import com.kjsp.kjspslide.dto.UserDto;
import com.kjsp.kjspslide.dto.UserDto.Request;
import com.kjsp.kjspslide.service.UserAuthService;
import com.kjsp.kjspslide.service.UserService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin
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

  @PostMapping(value = "/auth/check")
  public ResponseEntity<Object> authCheck(
      HttpServletRequest httpServletRequest,
      @RequestBody UserDto.Request userDto
  ) {
    if (httpServletRequest.getAttribute(JwtConstant.JWT_SUBJECT_TEXT) != null
        && httpServletRequest.getAttribute(JwtConstant.JWT_SUBJECT_TEXT)
        .equals(JwtConstant.JWT_EXPIRED_ACCESS_TOKEN)) {

      Map<String, String> token = userAuthService.createAccessToken(userDto);

      return new ResponseEntity<>(
          JwtDto.Response.builder()
              .accessToken(token.get(JwtConstant.JWT_ACCESS_TOKEN_TEXT))
              .refreshToken(token.get(JwtConstant.JWT_REFRESH_TOKEN_TEXT))
              .tokenType(JwtConstant.JWT_TOKEN_TYPE).build(),
          HttpStatus.OK);
    } else if (httpServletRequest.getAttribute(JwtConstant.JWT_SUBJECT_TEXT).equals(
        JwtConstant.JWT_EXPIRED_REFRESH_TOKEN
    )) {
      // 403 : 모든 토큰이 만료되었거나 정보가 없을 때 재 로그인
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(value = "/info")
  public ResponseEntity<Object> getUserInfo(
      @RequestBody UserDto.Request userDto
  ) {
    UserDto.Response responseDto = userService.getUserInfo(
        userDto.getUserEmail());
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
      Map<String, String> token = userAuthService.createAccessToken(userDto);
      UserDto.Response userInfo = userService.getUserInfo(
          userDto.getUserEmail());
      return new ResponseEntity<>(
          JwtDto.Response.builder()
              .userInfo(userInfo)
              .accessToken(token.get(JwtConstant.JWT_ACCESS_TOKEN_TEXT))
              .refreshToken(token.get(JwtConstant.JWT_REFRESH_TOKEN_TEXT))
              .tokenType("Bearer").build(),
          HttpStatus.OK);
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
