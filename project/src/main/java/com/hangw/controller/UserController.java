package com.hangw.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hangw.model.DataNotFoundException;
import com.hangw.model.MailDto;
import com.hangw.model.UserCreateForm;
import com.hangw.service.MailService;
import com.hangw.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final MailService mailService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "member2";
	}

	@PostMapping("/signup") // user회원가입 처리
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "member2";
		}

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			return "member2";
		}

		try {
			userService.create(userCreateForm.getUserName(), userCreateForm.getEmail(), userCreateForm.getPhone(),
					userCreateForm.getPassword1());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "member2";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "member2";
		}

		return "redirect:/";
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request, Model model) {
		String referrer = request.getHeader("Referer");
		request.getSession().setAttribute("prevPage", referrer);
		return "login";
	}

	@GetMapping("/findId")
	public String findId() {
		return "find-id";
	}

	@PostMapping("/find-id")
	public String findUserId(@RequestParam String name, @RequestParam String phone, Model model) {
		try {
			String userId = userService.getUserId(name, phone);
			model.addAttribute("userId", userId);
			return "findIdResult";
		} catch (DataNotFoundException e) {
			model.addAttribute("errorMessage", "해당 정보를 가진 사용자를 찾을 수 없습니다.");
			return "findIdResult";
		}
	}

	@GetMapping("/findPW")
	public String findPW() {
		return "find-password";
	}

	@PostMapping("/find-password")
	public ResponseEntity<?> findPassword(@RequestParam() String email, @RequestParam() String name,
			@RequestParam() String phone) {
// 이메일과 사용자 정보 확인
		if (!userService.isValidUser(email, name, phone)) {
			return new ResponseEntity<>("일치하는 정보가 없습니다.", HttpStatus.BAD_REQUEST);
		}

// 임시 비밀번호 생성 후 업데이트 및 전송
		String tmpPassword = userService.getTmpPassword();
		userService.updatePassword(tmpPassword, email);
		MailDto mail = mailService.createMail(tmpPassword, email);
		mailService.sendMail(mail);

		return new ResponseEntity<>("임시 비밀번호가 발급되었습니다. 이메일을 확인하세요.", HttpStatus.OK);
	}

}