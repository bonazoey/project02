package com.mycompany.backendapi.responseexclude.dto;

import lombok.Data;

@Data
public class LoginRequest {
	private String mid;
	private String mpassword;
}
