package com.kjsp.kjspslide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

  @GetMapping(value = "/real")
  public void test() {
    System.out.println("this is test.");
  }

}
