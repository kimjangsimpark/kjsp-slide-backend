package com.kjsp.kjspslide.configuration;

import com.kjsp.kjspslide.interceptor.BearerAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final BearerAuthInterceptor bearerAuthInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(bearerAuthInterceptor)
        .addPathPatterns("/api/user/info")
        .excludePathPatterns("/api/user/signin")
        .excludePathPatterns("/api/user/signup");
  }
}
