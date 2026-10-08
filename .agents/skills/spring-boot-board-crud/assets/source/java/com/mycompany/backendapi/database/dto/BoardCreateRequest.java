package com.mycompany.backendapi.database.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardCreateRequest {
	private String btitle;
	private String bcontent;
	private String bwriter;
	private MultipartFile battach;
}
