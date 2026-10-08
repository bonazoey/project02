package com.mycompany.backendapi.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {
	@NotBlank(message = "mid는 필수 입력 정보입니다.")
	@Size(min = 5, max = 10, message = "mid는 5자 이상, 10자 이하이어야 합니다.")
	private String mid;
	
	@NotBlank(message = "mpassword는 필수 입력 정보입니다.")
	@Size(min = 5, max = 10, message = "mid는 5자 이상, 10자 이하이어야 합니다.")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).+$",
	         message = "비밀번호는 대소문자를 포함하고, 특수문자를 최소 1개 포함해야 합니다.")
	private String mpassword;
}
