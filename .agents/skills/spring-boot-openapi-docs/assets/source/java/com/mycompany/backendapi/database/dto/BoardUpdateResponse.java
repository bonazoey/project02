package com.mycompany.backendapi.database.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BoardUpdateResponse {
	private int bno;
	private String btitle;
	private String bwriter;
	private int bhitcount;
	private Date bdate;
	private String battachoname;
	private String battachtype;
}
