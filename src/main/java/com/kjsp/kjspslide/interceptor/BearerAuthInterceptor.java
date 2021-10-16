package com.kjsp.kjspslide.interceptor;

import com.kjsp.kjspslide.service.JwtTokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {

  private final AuthorizationExtractor authExtractor;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler) {
    String token = authExtractor.extract(request, "Bearer");

    if (!jwtTokenProvider.validateToken(token)) {
      return false;
    }

    String subject = jwtTokenProvider.getSubject(token);
    request.setAttribute("subject", subject);
    return true;
  }

}
