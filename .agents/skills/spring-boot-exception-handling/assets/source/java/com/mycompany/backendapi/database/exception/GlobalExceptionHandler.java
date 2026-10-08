package com.mycompany.backendapi.database.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//basePackages내에 있는 모든 RestController에서 처리되지 않은 예외를 처리
@RestControllerAdvice(basePackages = {"com.mycompany.backendapi"})
public class GlobalExceptionHandler {
	@ExceptionHandler(NoAccountExistException.class)
	public Map<String, String> handleNoAccountExistException(NoAccountExistException e) {
		Map<String, String> map = new HashMap<>();
		map.put("result", "failure");
		map.put("message", e.getMessage());
		return map;
	}
	
	/*@ExceptionHandler(InsufficientFundsException.class)
	public Map<String, String> handleInsufficientFundsException(InsufficientFundsException e) {
		Map<String, String> map = new HashMap<>();
		map.put("result", "failure");
		map.put("message", "잔고 부족");
		return map;
	}*/
	
	//...
	
	@ExceptionHandler(Exception.class)
	public Map<String, String> handleException(Exception e) {
		Map<String, String> map = new HashMap<>();
		map.put("result", "failure");
		map.put("message", "알 수 없은 예외 발생. 조금 후 재사용해보세요.");
		return map;
	}
}






