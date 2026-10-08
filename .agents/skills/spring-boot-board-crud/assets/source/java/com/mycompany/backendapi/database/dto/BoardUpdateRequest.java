package com.mycompany.backendapi.database.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardUpdateRequest {
	private String btitle;
	private String bcontent;
	private int bno;
	private MultipartFile battach;
}
