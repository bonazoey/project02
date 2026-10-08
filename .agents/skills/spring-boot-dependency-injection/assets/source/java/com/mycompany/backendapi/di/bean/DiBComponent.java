package com.mycompany.backendapi.di.bean;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DiBComponent {
	public void method() {
		log.info("실행");
	}
}
