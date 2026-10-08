package com.mycompany.backendapi.di.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backendapi.di.service.DiBoardService;
import com.mycompany.backendapi.di.service.DiMemberService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/di")
@Slf4j
public class DiController {
	//필드 주입
	@Autowired
	private DiMemberService memberService;
	
	//필드 주입
	@Autowired
	private DiBoardService boardService;
	
	//컨트롤러는 반드시 기본 생성자만 사용해야함
	//컨트롤러는 생성자를 작성하지 않는게 원칙
	
	@PostMapping("/member-join")
	public String join() {
		memberService.join();
		return "가입 성공";
	}
	
	@GetMapping("/board-list")
	public String boardList() {
		boardService.list();
		return "목록 가져옴";
	}
	
}







