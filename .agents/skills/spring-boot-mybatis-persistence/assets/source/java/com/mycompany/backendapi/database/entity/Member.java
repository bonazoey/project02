package com.mycompany.backendapi.database.entity;

import lombok.Data;

@Data
public class Member {
	private String mid;
	private String mname;
	private String mpassword;
	private String memail;
	private boolean menabled;
	private String mrole;
}
