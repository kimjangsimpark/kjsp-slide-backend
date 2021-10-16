package com.kjsp.kjspslide.dto;

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

    private String accessToken;
    private String tokenType;
  }

}
