package com.mycompany.backendapi.database.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backendapi.database.entity.Account;

@Mapper
public interface AccountDao {
	public Account selectByAno(String ano);
	public int update(Account account);
}
