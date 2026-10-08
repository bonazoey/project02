package com.mycompany.backendapi.restcontroller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerTest {
	@RequestMapping("/")
	public String ui() {
		return "ui";
	}
}
