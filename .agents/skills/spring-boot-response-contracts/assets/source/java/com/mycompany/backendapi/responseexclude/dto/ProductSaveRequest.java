package com.mycompany.backendapi.responseexclude.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductSaveRequest {
	private String pname;
	private int pprice;
	private String pcompany;
	private MultipartFile pimage;
}
