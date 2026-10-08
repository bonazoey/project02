package com.mycompany.backendapi.database.dto;

import lombok.Data;

@Data
public class MemberLoginResponse {
	private String result; 		//success, wrong-mid, wrong-mpassword 중 하나 저장
	private String message;		//실패에 대한 상세 내용 저장
	private String accessToken; //성공시에 JWT 토큰 저장
}
