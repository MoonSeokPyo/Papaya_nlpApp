package com.hangw.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hangw.model.DataNotFoundException;
import com.hangw.model.MailDto;
import com.hangw.model.PageUser;
import com.hangw.model.Review;
import com.hangw.model.UserCreateForm;
import com.hangw.service.MailService;
import com.hangw.service.ReviewService;
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
	private final ReviewService reviewService;
	private final PasswordEncoder passwordEncoder;
	
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
	public ResponseEntity<Map<String, String>> findPassword(
	        @RequestParam String email, @RequestParam String name,
	        @RequestParam String phone) {

	    Map<String, String> response = new HashMap<>();

	    if (!userService.isValidUser(email, name, phone)) {
	        response.put("message", "일치하는 정보가 없습니다.");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    String tmpPassword = userService.getTmpPassword();
	    userService.updatePassword(tmpPassword, email);
	    MailDto mail = mailService.createMail(tmpPassword, email);
	    mailService.sendMail(mail);

	    response.put("message", "임시 비밀번호가 발급되었습니다. 이메일을 확인하세요.");
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@GetMapping("/userpage")
	@PreAuthorize("isAuthenticated")
	public String userPage(Model model, Principal principal) {
	    String email = principal.getName();
	    PageUser user = userService.getUser(email);
	    List<Review> reviews = reviewService.getReviewsByWriter(user);

	    model.addAttribute("user", user);
	    model.addAttribute("reviews", reviews);
	    return "userpage1";
	}
	
	@GetMapping("/change-password")
	public String showChangePasswordPage() {
	    return "change-password";
	}
	
	@PostMapping("/change-password")
	public String changePassword(Principal principal,
	                             @RequestParam() String currentPassword,
	                             @RequestParam() String newPassword,
	                             @RequestParam() String confirmPassword,
	                             Model model,
	                             RedirectAttributes redirectAttributes) {

	    String email = principal.getName(); // 현재 로그인한 사용자의 이메일
	    PageUser user = userService.getUser(email);

	    if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
	        model.addAttribute("errorMessage", "현재 비밀번호가 일치하지 않습니다.");
	        return "change-password";
	    }

	    if (!newPassword.equals(confirmPassword)) {
	        model.addAttribute("errorMessage", "새 비밀번호가 일치하지 않습니다.");
	        return "change-password";
	    }
	    
	    if (passwordEncoder.matches(newPassword, user.getPassword())) {
	        model.addAttribute("errorMessage", "새 비밀번호는 현재 비밀번호와 다르게 설정해야 합니다.");
	        return "change-password";
	    }

	    // 새 비밀번호로 업데이트
	    userService.updatePassword(newPassword, email);
	    redirectAttributes.addFlashAttribute("successMessage", "비밀번호가 성공적으로 변경되었습니다.");

	    return "redirect:/user/userpage";
	}
	
}