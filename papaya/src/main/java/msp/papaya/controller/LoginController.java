package msp.papaya.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {

  @GetMapping("/login")
  public String loginPage() {
    return "login"; // login.html 템플릿
  }
  @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        // 사용자 정보를 가져옵니다.

        return "/";
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "/login";
    }

  @GetMapping("/login/oauth2/code/kakao")
  public @ResponseBody String kakaoCallback(String code) { // Data 리턴해주는 컨트롤러 함수
    return "카카오 인증 완료 : 코드값 :" + code;
  }
}
