package com.kjsp.kjspslide.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements Serializable {

  private static final long serialVersionUID = -7445315487575946669L;

  @Getter
  @Builder
  @AllArgsConstructor
  public static class Info {

    private String userName;
    private String userPassword;
    private String userEmail;
    private String userPhone;
  }

  @Getter
  @Setter
  public static class Request {

    private String userName;
    private String userPassword;
    private String userEmail;
    private String userPhone;
  }

  @Getter
  @Builder
  public static class Response {

    private String userName;
    private String userEmail;
    private String userPhone;
  }
}
