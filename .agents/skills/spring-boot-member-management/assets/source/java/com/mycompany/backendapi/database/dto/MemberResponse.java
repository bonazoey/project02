package com.mycompany.backendapi.database.dto;

import lombok.Data;

@Data
public class MemberResponse {
	private String mid;
	private String mname;
	private String memail;
	private boolean menabled;
	private String mrole;
}
