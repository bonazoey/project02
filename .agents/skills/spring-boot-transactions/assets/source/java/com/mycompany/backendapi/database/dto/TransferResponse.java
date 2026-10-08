package com.mycompany.backendapi.database.dto;

import lombok.Data;

@Data
public class TransferResponse {
	private String result; 	//success, failure
	private String message; // failure 원인
}
