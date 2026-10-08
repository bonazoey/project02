package com.mycompany.backendapi.database.dto;

import lombok.Data;

@Data
public class TransferRequest {
	private String from;
	private String to;
	private int amount;
}
