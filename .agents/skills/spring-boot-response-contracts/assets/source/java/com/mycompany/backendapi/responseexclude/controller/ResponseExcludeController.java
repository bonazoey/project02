package com.mycompany.backendapi.responseexclude.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backendapi.responseexclude.dto.AiMessageResponse;
import com.mycompany.backendapi.responseexclude.dto.Board;
import com.mycompany.backendapi.responseexclude.dto.BoardResponse;
import com.mycompany.backendapi.responseexclude.dto.BoardSaveRequest;
import com.mycompany.backendapi.responseexclude.dto.BoardUpdateRequest;
import com.mycompany.backendapi.responseexclude.dto.LoginRequest;
import com.mycompany.backendapi.responseexclude.dto.LoginResponse;
import com.mycompany.backendapi.responseexclude.dto.ProductResponse;
import com.mycompany.backendapi.responseexclude.dto.ProductSaveRequest;
import com.mycompany.backendapi.responseexclude.dto.UserMessageRequest;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/response-exclude")
@Slf4j
public class ResponseExcludeController {
	//게시물 등록
	@PostMapping("/board-save1")
	public String boardSave1(@ModelAttribute Board board) {
		log.info(board.toString());
		return "저장 성공";
	}
	
	@PostMapping("/board-save2")
	public String boardSave2(@ModelAttribute BoardSaveRequest boardRequest) {
		log.info(boardRequest.toString());
		return "저장 성공";
	}	
	
	//게시물 보기
	@GetMapping("/board-read1")
	public Board boardRead1() {
		Board board = new Board();
		board.setBno(1);
		board.setBtitle("제목입니다.");
		board.setBcontent("내용입니다.");
		board.setBwriter("사용자");
		board.setBdate(new Date());
		return board;
	}	
	
	@GetMapping("/board-read2")
	public BoardResponse boardRead2() {
		BoardResponse board = new BoardResponse();
		board.setBno(1);
		board.setBtitle("제목입니다.");
		board.setBcontent("내용입니다.");
		board.setBwriter("사용자");
		board.setBdate(new Date());
		return board;
	}
	
	@PostMapping("/board-update")
	public String boardUpdate1(@ModelAttribute BoardUpdateRequest boardUpdateRequest) {
		log.info(boardUpdateRequest.toString());
		return "수정 성공";
	}
	
	@PostMapping("/product-save")
	public String productSave(@ModelAttribute ProductSaveRequest productSaveRequest) {
		log.info(productSaveRequest.toString());
		return "등록 성공";
	}
	
	@GetMapping("/product-read")
	public ProductResponse productRead(@RequestParam("pid") int pid) {
		log.info("pid: " + pid);
		ProductResponse productResponse = new ProductResponse();
		productResponse.setPid(pid);
		productResponse.setPname("카메라");
		productResponse.setPprice(500000);
		productResponse.setPcompany("삼성전자");
		productResponse.setPimageoname("original.png");
		productResponse.setPimagesname("save.png");
		productResponse.setPimagetype("image/png");
		return productResponse;
	}
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {
		log.info(loginRequest.toString());
		
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setMid(loginRequest.getMid());
		loginResponse.setAccessToken("xxxxx");
		return loginResponse;
	}
	
	@PostMapping("/chat")
	public AiMessageResponse chat(@RequestBody UserMessageRequest umr) {
		log.info(umr.toString());
		
		AiMessageResponse amr = new AiMessageResponse();
		amr.setQuestion(umr.getQuestion());
		amr.setAnswer("삼성전자의 오늘 주가는 230000");
		return amr;
	}
}










