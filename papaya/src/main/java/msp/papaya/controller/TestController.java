package msp.papaya.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @GetMapping("/hello")
  public String hello() {
    return "Hello, World!";
  }

  @GetMapping("/springtest")
  public String home(Model model) {
    model.addAttribute("message", "Spring Boot에서 안녕하세요!");
    return "springtest";
  }
}