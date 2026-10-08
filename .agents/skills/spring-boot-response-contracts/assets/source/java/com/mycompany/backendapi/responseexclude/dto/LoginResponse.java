package com.mycompany.backendapi.responseexclude.dto;

import lombok.Data;

@Data
public class LoginResponse {
	private String mid;
	private String accessToken;
}
