package com.mycompany.backendapi.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mycompany.backendapi.di.bean.DiAComponent;
import com.mycompany.backendapi.di.bean.DiBComponent;
import com.mycompany.backendapi.di.bean.DiInterface;
import com.mycompany.backendapi.di.bean.DiInterfaceImplB;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiMemberService {
	//필드 주입
	@Autowired
	private DiAComponent aComponent;
	
	@Autowired @Qualifier("diInterfaceImplA")
	private DiInterface implA;	
	
	private DiBComponent bComponent;
	private DiInterface implB;
	
	//생성자 주입
	public DiMemberService(
			DiBComponent bcomponent, 
			@Qualifier("diInterfaceImplB") DiInterface implB) {
		this.bComponent = bcomponent;
		this.implB = implB;
	}
	
	public void join() {
		log.info("실행");
		aComponent.method();
		bComponent.method();
		implA.method();
		implB.method();
	}
	
	public void login() {
		log.info("실행");
	}
}
