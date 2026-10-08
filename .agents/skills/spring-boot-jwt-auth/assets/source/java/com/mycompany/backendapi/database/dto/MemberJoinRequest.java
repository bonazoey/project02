package com.mycompany.backendapi.database.dto;

import lombok.Data;

@Data
public class MemberJoinRequest {
	private String mid;
	private String mname;
	private String mpassword;
	private String memail;
}
