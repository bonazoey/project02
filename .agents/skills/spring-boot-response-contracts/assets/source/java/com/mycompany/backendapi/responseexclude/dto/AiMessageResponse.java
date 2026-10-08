package com.mycompany.backendapi.responseexclude.dto;

import lombok.Data;

@Data
public class AiMessageResponse {
	private String question;
	private String answer;
}
