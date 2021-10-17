package com.kjsp.kjspslide.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private final String accessTokenSecretKey;
  private final String refreshTokenSecretKey;
  private final long validityInMillisecondsHour;
  private final long validityInMillisecondsWeek;

  public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String accessTokenSecretKey,
      @Value("${security.jwt.refresh.token.secret-key}") String refreshTokenSecretKey,
      @Value("${security.jwt.token.expire-length}") long validityInMillisecondsHour,
      @Value("${security.jwt.refresh.token.expire-length}") long validityInMillisecondsWeek) {
    this.accessTokenSecretKey = Base64.getEncoder().encodeToString(accessTokenSecretKey.getBytes());
    this.refreshTokenSecretKey = Base64.getEncoder()
        .encodeToString(refreshTokenSecretKey.getBytes());
    this.validityInMillisecondsHour = validityInMillisecondsHour;
    this.validityInMillisecondsWeek = validityInMillisecondsWeek;
  }

  //토큰생성
  public Map<String, String> createToken(String subject) {
    Claims claims = Jwts.claims().setSubject(subject);
    Map<String, String> tokenList = new HashMap<>();

    // create Access Token
    Date now = new Date();

    Date validityHour = new Date(now.getTime()
        + validityInMillisecondsHour);

    tokenList.put("accessToken", Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validityHour)
        .signWith(SignatureAlgorithm.HS256, accessTokenSecretKey)
        .compact());

    // create Refresh Token
    Date validityWeek = new Date(now.getTime()
        + validityInMillisecondsWeek);

    tokenList.put("refreshToken", Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validityWeek)
        .signWith(SignatureAlgorithm.HS256, refreshTokenSecretKey)
        .compact());

    return tokenList;
  }

  //토큰에서 값 추출
  public String getAccessTokenSubject(String token) {
    return Jwts.parser().setSigningKey(accessTokenSecretKey).parseClaimsJws(token).getBody()
        .getSubject();
  }

  //유효한 엑세스 토큰인지 확인
  public boolean validateAccessToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(accessTokenSecretKey).parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  //유효한 리프레쉬 토큰인지 확인
  public boolean validateRefreshToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(refreshTokenSecretKey).parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
