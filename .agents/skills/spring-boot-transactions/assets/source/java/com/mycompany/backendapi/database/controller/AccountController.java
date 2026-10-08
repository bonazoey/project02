package com.mycompany.backendapi.database.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backendapi.database.dto.TransferRequest;
import com.mycompany.backendapi.database.dto.TransferResponse;
import com.mycompany.backendapi.database.entity.Transfer;
import com.mycompany.backendapi.database.exception.InsufficientFundsException;
import com.mycompany.backendapi.database.exception.NoAccountExistException;
import com.mycompany.backendapi.database.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/database/account")
@Slf4j
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/transfer")
	public TransferResponse transfer(@RequestBody TransferRequest request) {
		Transfer transfer = new Transfer();
		transfer.setFrom(request.getFrom());
		transfer.setTo(request.getTo());
		transfer.setAmount(request.getAmount());
		
		/*TransferResponse transferResponse = new TransferResponse();
		try {
			accountService.transfer(transfer);
			transferResponse.setResult("success");
		} catch(InsufficientFundsException e) {
			transferResponse.setResult("failure");
			transferResponse.setMessage("잔고 부족");
		} catch(NoAccountExistException e) {
			transferResponse.setResult("failure");
			transferResponse.setMessage(e.getMessage());
		}*/	
		
		TransferResponse transferResponse = new TransferResponse();
		accountService.transfer(transfer);
		transferResponse.setResult("success");
		
		return transferResponse;
	}
}









