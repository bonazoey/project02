package com.mycompany.backendapi.receivedata.dto;

import lombok.Data;

@Data
public class LoginRequest {
	private String mid;
	private String mpassword;
}
