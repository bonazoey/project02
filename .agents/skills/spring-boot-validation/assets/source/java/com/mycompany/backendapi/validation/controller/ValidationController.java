package com.mycompany.backendapi.validation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backendapi.validation.dto.JoinRequest;
import com.mycompany.backendapi.validation.dto.LoginRequest;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/validation")
@Slf4j
public class ValidationController {
	@PostMapping("/join")
	public String join(@Valid @RequestBody JoinRequest joinRequest) {
		return "가입 성공";
	}
	
	@PostMapping("/login")
	public String login(@Valid @RequestBody LoginRequest loginRequest) {
		return "로그인 성공";
	}
}
