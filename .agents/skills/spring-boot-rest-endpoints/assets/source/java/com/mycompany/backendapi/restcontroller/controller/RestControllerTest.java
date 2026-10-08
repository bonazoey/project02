package com.mycompany.backendapi.restcontroller.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backendapi.restcontroller.dto.Board;

@RestController
@RequestMapping("/restcontroller")
public class RestControllerTest {
	@RequestMapping("/")
	public String index() {
		return "Spring Back End Home";
	}
	
	@RequestMapping(value="/getBoard1", produces = "application/json")
	public Board getBoard1() {
		Board board = new Board(1, "제목1", "내용1", "사용자1", 0, new Date());
		return board;
	}
	
	@RequestMapping(value="/getBoard2", produces = "text/xml")
	public Board getBoard2() {
		Board board = new Board(1, "제목1", "내용1", "사용자1", 0, new Date());
		return board;
	}
}
