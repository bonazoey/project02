package com.mycompany.backendapi.responseexclude.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardUpdateRequest {
	private int bno;
	private String btitle;
	private String bcontent;
	private String bwriter;
	private MultipartFile battch;
}
