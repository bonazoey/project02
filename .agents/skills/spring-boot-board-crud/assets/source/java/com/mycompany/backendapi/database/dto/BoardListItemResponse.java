package com.mycompany.backendapi.database.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BoardListItemResponse {
	private int bno;
	private String btitle;
	private String bwriter;
	private Date bdate;
	private int bhitcount;
}
