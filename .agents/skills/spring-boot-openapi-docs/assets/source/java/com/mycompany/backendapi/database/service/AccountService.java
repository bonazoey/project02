package com.mycompany.backendapi.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.backendapi.database.dao.AccountDao;
import com.mycompany.backendapi.database.entity.Account;
import com.mycompany.backendapi.database.entity.Transfer;
import com.mycompany.backendapi.database.exception.InsufficientFundsException;
import com.mycompany.backendapi.database.exception.NoAccountExistException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService {
	@Autowired
	private AccountDao accountDao;
	
	@Transactional
	public void transfer(Transfer transfer) throws NoAccountExistException, InsufficientFundsException {
		//출금하기 ---------------------
		Account fromAccount = accountDao.selectByAno(transfer.getFrom());
		if(fromAccount == null) {
			throw new NoAccountExistException("출금 계좌 없음");
		}
		if(fromAccount.getBalance() < transfer.getAmount()) {
			throw new InsufficientFundsException();
		}
		fromAccount.setBalance(fromAccount.getBalance() - transfer.getAmount());
		accountDao.update(fromAccount);
		
		//입금하기 ---------------------
		Account toAccount = accountDao.selectByAno(transfer.getTo());
		if(toAccount == null) {
			throw new NoAccountExistException("입금 계좌 없음");
		}
		toAccount.setBalance(toAccount.getBalance() + transfer.getAmount());
		accountDao.update(toAccount);
	}
}












