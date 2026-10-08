package com.mycompany.backendapi.responseexclude.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	private String btitle;
	private String bcontent;
	private String bwriter;
	
	//응답에서 제외
	@JsonProperty(access = Access.WRITE_ONLY)
	private MultipartFile battach; // 업로드된 파일을 받을때
	
	//응답에서 제외
	@JsonProperty(access = Access.WRITE_ONLY)
	private byte[] battachdata;	   // 업로드된 파일 데이터
	
	private int bno;	
	private int bhitcount;	
	private Date bdate;	
	private String battachoname;   // MultipartFile에서 원래 파일 이름을 추출해서 저장	
	private String battachsname;   // 서버 파일 시스템에 저장할때 사용할 파일 이름	
	private String battachtype;    // 파일의 타입(image/jpeg, image/png)
}
