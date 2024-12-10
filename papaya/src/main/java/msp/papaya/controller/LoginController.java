package msp.papaya.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
  @GetMapping("/login/oauth2/code/kakao")
  public @ResponseBody String kakaoCallback(String code) { // Data 리턴해주는 컨트롤러 함수
    return "카카오 인증 완료 : 코드값 :" + code;
  }
}
