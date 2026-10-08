package com.mycompany.backendapi.database.entity;

import lombok.Data;

@Data
public class Transfer {
	private String from;
	private String to;
	private int amount;
}
