package com.kjsp.kjspslide.service;

import com.kjsp.kjspslide.dto.UserDto.Request;
import com.kjsp.kjspslide.dto.UserDto.Response;
import com.kjsp.kjspslide.entity.UserInfo;
import com.kjsp.kjspslide.repository.UserInfoRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private static final int SALT_SIZE = 16;
  private final UserInfoRepository userInfoRepository;

  @Transactional
  public boolean validateUserPassword(Request userDto) {
    try {
      UserInfo userInfo = userInfoRepository.findUserInfoByUserEmail(userDto.getUserEmail());
      String salt = userInfo.getSalt();
      String tempPassword = hashing(userDto.getUserPassword().getBytes(StandardCharsets.UTF_8),
          salt);
      if (userInfo.getUserPassword().equals(tempPassword)) {
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      log.error("validateUserPasswordError : " + e);
      return false;
    }
  }

  @Transactional()
  public Response getUserInfo(String subject) {
    try {
      UserInfo userInfo = userInfoRepository.findUserInfoByUserEmail(subject);
      return Response.builder()
          .userEmail(userInfo.getUserEmail())
          .userName(userInfo.getUserName())
          .userPhone(userInfo.getUserPhone())
          .build();
    } catch (Exception e) {
      log.error("getUserInfoError : " + e);
      return null;
    }
  }

  @Transactional()
  public HttpStatus saveUserInfo(Request userDto) {

    try {
      UserInfo userInfo = userInfoRepository.findUserInfoByUserEmail(userDto.getUserEmail());
      if (userInfo != null && userInfo.getUserEmail().equals(userDto.getUserEmail())) {
        return HttpStatus.BAD_REQUEST;
      }

      String salt = getSalt();

      userInfoRepository.save(UserInfo.builder()
          .userEmail(userDto.getUserEmail())
          .userName(userDto.getUserName())
          .userPhone(userDto.getUserPhone())
          .userPassword(hashing(userDto.getUserPassword().getBytes(StandardCharsets.UTF_8), salt))
          .salt(salt)
          .build());
    } catch (Exception e) {
      log.error("saveUserInfoError : " + e);
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    return HttpStatus.OK;
  }

  // ???????????? ??????
  private String hashing(byte[] password, String Salt) throws Exception {

    MessageDigest md = MessageDigest.getInstance("SHA-256");  // SHA-256 ??????????????? ??????

    // key-stretching
    for (int i = 0; i < 3; i++) {
      String temp = byteToString(password) + Salt;  // ??????????????? Salt ??? ?????? ????????? ????????? ??????
      md.update(temp.getBytes());            // temp ??? ???????????? ???????????? md ??? ???????????????
      password = md.digest();              // md ????????? ?????????????????? ?????? password ??? ????????????
    }

    return byteToString(password);
  }


  // SALT ??? ??????
  private String getSalt() {
    SecureRandom rnd = new SecureRandom();
    byte[] temp = new byte[SALT_SIZE];
    rnd.nextBytes(temp);

    return byteToString(temp);

  }


  // ????????? ?????? 16????????? ???????????????
  private String byteToString(byte[] temp) {
    StringBuilder sb = new StringBuilder();
    for (byte a : temp) {
      sb.append(String.format("%02x", a));
    }
    return sb.toString();
  }

}
