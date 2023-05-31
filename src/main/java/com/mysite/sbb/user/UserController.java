package com.mysite.sbb.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.sbb.email.EmailService;
import com.mysite.sbb.email.RedisUtill;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	@Autowired
	private RedisUtill redisUtill;

	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}

	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}

		userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());

		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {
		return "login_form";
	}

	/* 회원가입 유효성 검증 post */

	@PostMapping("/check")
	public ResponseEntity<Integer> checkUsername(@RequestParam("username") String username) {
		List<SiteUser> users = userService.check(username);
		int result = (users != null && !users.isEmpty()) ? 1 : 0;
		return ResponseEntity.ok(result);
	}

	@GetMapping("/sign")
	public String logi2n() {
		return "signup";
	}

	private final EmailService emailService;

	@PostMapping("/emailcode")
	@ResponseBody
	public String mailConfirm(@RequestParam String email) throws Exception {
		String code = emailService.sendSimpleMessage(email);
		return code;
	}

	@GetMapping("/find_pw")
	public String findPW() {
		return "find_pw";
	}
	/*
	 * @PostMapping("/temp_pwd")
	 * 
	 * @ResponseBody public String sendTempPwd(@RequestParam String email) throws
	 * Exception { String code = emailService.sendTempMessage(email); return code; }
	 */
}