package com.kjsp.kjspslide.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class JwtDto implements Serializable {

  private static final long serialVersionUID = 8535852073040666208L;

  @Getter
  @Builder
  @AllArgsConstructor
  public static class Response {

    @JsonInclude(Include.NON_NULL)
    private UserDto.Response userInfo;
    private String refreshToken;
    private String accessToken;
    private String tokenType;
  }

}
