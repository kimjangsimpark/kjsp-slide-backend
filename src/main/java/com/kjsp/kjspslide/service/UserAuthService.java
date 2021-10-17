package com.kjsp.kjspslide.service;

import com.kjsp.kjspslide.dto.UserDto;
import com.kjsp.kjspslide.entity.UserInfo;
import com.kjsp.kjspslide.repository.UserInfoRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthService {

  private final UserInfoRepository userInfoRepository;
  private final JwtTokenProvider jwtTokenProvider;


  // 토큰 생성하는 메서드
  public Map<String, String> createAccessToken(
      UserDto.Request userInfoDto) { // 토큰에 담고싶은 값 파라미터로 가져오기
    UserInfo userInfo = userInfoRepository.findUserInfoByUserEmail(userInfoDto.getUserEmail());
    return jwtTokenProvider.createToken(userInfo.getUserEmail());
  }

}
