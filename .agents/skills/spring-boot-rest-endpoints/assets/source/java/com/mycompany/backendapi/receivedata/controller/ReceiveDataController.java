package com.mycompany.backendapi.receivedata.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.backendapi.receivedata.dto.LoginRequest;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/receive-data")
@Slf4j
public class ReceiveDataController {
	@GetMapping("/path-variable/{uid}/{bno}")
	public String pathVariable(
			@PathVariable("uid") String mid, 
			@PathVariable("bno") int bno) {
		log.info("mid: " + mid);
		log.info("bno: " + bno);
		return "pathVariable 결과";
	}
	
	@GetMapping("/request-param")
	public String requestParam(
			@RequestParam("mid") String mid, 
			@RequestParam("bno") int bno) {
		log.info("mid: " + mid);
		log.info("bno: " + bno);
		return "requestParam 결과";
	}
	
	/*@PostMapping("/model-attribute")
	public String modelAttribute(
			@RequestParam("mid") String mid, 
			@RequestParam("mpassword") String mpassword) {
		log.info("mid: " + mid);
		log.info("mpassword: " + mpassword);
		return "modelAttribute 결과";
	}*/
	
	@PostMapping("/model-attribute")
	public String modelAttribute(@ModelAttribute LoginRequest loginRequest) {
		log.info("mid: " + loginRequest.getMid());
		log.info("mpassword: " + loginRequest.getMpassword());
		log.info(loginRequest.toString());
		return "modelAttribute 결과";
	}
	
	@PostMapping("/request-body")
	public String requestBody(@RequestBody LoginRequest loginRequest) {
		log.info("mid: " + loginRequest.getMid());
		log.info("mpassword: " + loginRequest.getMpassword());
		log.info(loginRequest.toString());
		return "requestBody결과";
	}
	
	@PostMapping("/multipart")
	public String multipart(
			@RequestPart("title") String title, 
			@RequestPart("attach") MultipartFile attach) throws Exception {
		// 문자 파트
		log.info("title: " + title);
		
		// 파일 파트
		log.info("attach file name: " + attach.getOriginalFilename());
		log.info("attach file type: " + attach.getContentType());	
		
		//방법1
		/*byte[] fileData = attach.getBytes();
		String strData = new String(fileData);
		log.info("attach file data: " + strData);*/
		
		//방법2
		InputStream inputStream = attach.getInputStream();
		Reader reader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String strData = "";
		while(true) {
			String data = bufferedReader.readLine();
			if(data == null) {
				break;
			}
			strData += data + "\n";
		}
		log.info("attach file data: " + strData);
		
		return "multipart 결과";
	}
	
	@GetMapping("/request-header")
	public String requestHeader(@RequestHeader("User-Agent") String userAgent) {
		log.info("User-Agent: " + userAgent);
		return "requestHeader 결과";
	}
}





















