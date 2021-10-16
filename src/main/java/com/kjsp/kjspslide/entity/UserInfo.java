package com.kjsp.kjspslide.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "USER_INFO")
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

  // 기본키 매핑
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID", nullable = false)
  private Long userId;

  @Column(name = "SALT", nullable = false)
  private String salt;

  @Column(name = "USER_NAME", nullable = false)
  private String userName;

  @Column(name = "USER_PASSWORD", nullable = false)
  private String userPassword;

  @Column(name = "USER_EMAIL", nullable = false)
  private String userEmail;

  @Column(name = "USER_PHONE", nullable = false)
  private String userPhone;

}
