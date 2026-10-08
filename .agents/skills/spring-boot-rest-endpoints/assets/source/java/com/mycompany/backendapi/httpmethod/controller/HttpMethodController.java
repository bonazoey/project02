package com.mycompany.backendapi.httpmethod.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/http-method")
@Slf4j
public class HttpMethodController {
	//@RequestMapping(value="/info", method = RequestMethod.GET)
	@GetMapping("/info")
	public String info() {
		log.info("실행");
		return "info 결과";
	}
	
	//@RequestMapping(value="/join", method = RequestMethod.POST)
	@PostMapping("/join")
	public String join() {
		log.info("실행");
		return "join 결과";
	}
	
	@PutMapping("/modify")
	public String modify() {
		log.info("실행");
		return "modify 결과";
	}
	
	@DeleteMapping("/remove")
	public String remove() {
		log.info("실행");
		return "remove 결과";
	}
}








