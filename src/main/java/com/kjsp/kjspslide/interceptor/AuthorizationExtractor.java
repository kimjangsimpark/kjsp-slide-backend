package com.kjsp.kjspslide.interceptor;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationExtractor {

  public String extractToken(HttpServletRequest request, String type, String header) {
    Enumeration<String> headers = request.getHeaders(header);
    while (headers.hasMoreElements()) {
      String value = headers.nextElement();
      if (value.toLowerCase().startsWith(type.toLowerCase())) {
        return value.substring(type.length()).trim();
      }
    }

    return Strings.EMPTY;
  }
}
