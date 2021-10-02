package com.kjsp.kjspslide.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user_info")
public class UserInfo {

  // 기본키 매핑
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID", nullable = false)
  private Long userId;

  @Column(name = "USER_NAME", nullable = false)
  private String userName;

  @Column(name = "USER_PASSWORD", nullable = false)
  private String userPassword;

  @Column(name = "USER_EMAIL", nullable = false)
  private String userEmail;

  @Column(name = "USER_PHONE", nullable = false)
  private String userPhone;


}
