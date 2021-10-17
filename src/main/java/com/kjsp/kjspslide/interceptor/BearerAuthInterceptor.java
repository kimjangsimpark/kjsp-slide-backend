package com.kjsp.kjspslide.interceptor;

import com.kjsp.kjspslide.constant.JwtConstant;
import com.kjsp.kjspslide.service.JwtTokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {

  private final AuthorizationExtractor authExtractor;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public boolean preHandle(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler) {
    String accessToken = authExtractor.extractToken(request, JwtConstant.JWT_TOKEN_TYPE,
        JwtConstant.JWT_HEADER_AUTHORIZATION);

    if (accessToken.isEmpty()) {
      return true;
    }

    if (!jwtTokenProvider.validateAccessToken(accessToken)) {
      String refreshToken = authExtractor.extractToken(request, JwtConstant.JWT_TOKEN_TYPE,
          JwtConstant.JWT_HEADER_REFRESH_TOKEN);
      if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
        request.setAttribute(JwtConstant.JWT_SUBJECT_TEXT, JwtConstant.JWT_EXPIRED_TOKEN);
        return true;
      }
      return true;
    }

    String subject = jwtTokenProvider.getAccessTokenSubject(accessToken);
    request.setAttribute(JwtConstant.JWT_SUBJECT_TEXT, subject);
    return true;
  }

}
