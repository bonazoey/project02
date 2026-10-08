package com.mycompany.backendapi.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.backendapi.di.bean.DiAComponent;
import com.mycompany.backendapi.di.bean.DiBComponent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiBoardService {
	//필드 주입
	@Autowired
	private DiAComponent aComponent;
	
	private DiBComponent bComponent;
	
	//생성자 주입
	public DiBoardService(DiBComponent bComponent) {
		this.bComponent = bComponent;
	}
	
	public void write() {
		log.info("실행");
	}
	
	public void list() {
		log.info("실행");
		aComponent.method();
		bComponent.method();
	}
	
	public void update() {
		log.info("실행");
	}
	
	public void delete() {
		log.info("실행");
	}
}
