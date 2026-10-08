package com.mycompany.backendapi.returntype.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backendapi.returntype.dto.Board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/return-type")
@Slf4j
public class ReturnTypeController {
	@GetMapping("/string")
	public String string() {
		return "Hello, World!";
	}
	
	//@GetMapping("/dto")
	@GetMapping(value="/dto", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Board dto() {
		Board board = new Board();
		board.setBno(1);
		board.setBtitle("제목입니다.");
		board.setBcontent("내용입니다.");
		board.setBwriter("작성자입니다.");
		board.setBhitcount(0);
		board.setBdate(new Date());
		return board;
	}
	
	@GetMapping("/list")
	public List<Board> list() {
		List<Board> list = new ArrayList<>();
		
		for(int i=1; i<=5; i++) {
			Board board = new Board();
			board.setBno(i);
			board.setBtitle(i + "번째 제목입니다.");
			board.setBcontent(i + "번째 내용입니다.");
			board.setBwriter("사용자");
			board.setBhitcount(0);
			board.setBdate(new Date());
			list.add(board);
		}
		return list;
	}
	
	@GetMapping("/map")
	public Map<String, Object> map() {
		Map<String, Object> map = new HashMap<>();
		
		map.put("mid", "사용자");
		
		List<Board> list = new ArrayList<>();
		for(int i=1; i<=5; i++) {
			Board board = new Board();
			board.setBno(i);
			board.setBtitle(i + "번째 제목입니다.");
			board.setBcontent(i + "번째 내용입니다.");
			board.setBwriter("사용자");
			board.setBhitcount(0);
			board.setBdate(new Date());
			list.add(board);
		}
		
		map.put("boards", list);
		
		return map;
	}
	
	@GetMapping("/response-entity")
	public ResponseEntity<Board> responseEntity() {
		Board board = new Board();
		board.setBno(1);
		board.setBtitle("제목입니다.");
		board.setBcontent("내용입니다.");
		board.setBwriter("사용자");
		board.setBhitcount(0);
		board.setBdate(new Date());
		
		//커스텀 헤더 추가
		HttpHeaders headers = new HttpHeaders();
		headers.add("my-head", "my-value");						
		
		//return new ResponseEntity<>(board, headers, HttpStatus.OK);
		return ResponseEntity
				.status(HttpStatus.OK)
				.headers(headers)
				.body(board);
		
		//return ResponseEntity.ok(board);
	}
	
	@GetMapping("/http-entity")
	public HttpEntity<Board> httpEntity() {
		Board board = new Board();
		board.setBno(1);
		board.setBtitle("제목입니다.");
		board.setBcontent("내용입니다.");
		board.setBwriter("사용자");
		board.setBhitcount(0);
		board.setBdate(new Date());
		
		//커스텀 헤더 추가
		HttpHeaders headers = new HttpHeaders();
		headers.add("my-head", "my-value");						
		
		return new HttpEntity<>(board, headers);
	}	
	
	@GetMapping("/void")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("실행");
		//응답을 직접 생성
		//1) Content-Type 설정: 응답 본문의 데이터 형식
		//Content-Type: image/jpeg
		response.setContentType("image/jpeg");
		//response.addHeader("Content-Type", "image/jpeg");
		
		//2) Body에 있는 데이터를 브라우저가 바로 보여주지말고 파일 다운로드를 하도록 설정
		String fileName = "포트1.jpg";
		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		//3) 응답 본문에 데이터를 싣기(출력)
		//방법1: 파일의 크기가 클 경우
		/*InputStream is = new FileInputStream("C:\\kosa-course\\projects\\backend-spring\\back-end-api\\src\\main\\resources\\static\\images\\photo1.jpg");
		OutputStream os = response.getOutputStream();
		
		byte[] buffer = new byte[1024];
		while(true) {
			int num = is.read(buffer);
			if(num == -1) break;
			os.write(buffer, 0, num);
		}
		os.flush();
		
		is.close();
		os.close();*/
		
		//방법2: 파일 크기가 작을 경우
		Files.copy(
		  Path.of("C:\\kosa-course\\projects\\backend-spring\\back-end-api\\src\\main\\resources\\static\\images\\photo1.jpg"), 
		  response.getOutputStream()
		);
	}
}







