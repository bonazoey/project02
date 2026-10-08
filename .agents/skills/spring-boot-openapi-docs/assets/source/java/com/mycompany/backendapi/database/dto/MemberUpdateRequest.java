package com.mycompany.backendapi.database.dto;

import lombok.Data;

@Data
public class MemberUpdateRequest {
	private String mid;
	private String mpassword;
	private String memail;
	private boolean menabled;
}
