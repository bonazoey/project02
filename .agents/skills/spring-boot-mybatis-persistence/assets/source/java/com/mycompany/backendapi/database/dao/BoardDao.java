package com.mycompany.backendapi.database.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backendapi.database.dto.Pager;
import com.mycompany.backendapi.database.entity.Board;

@Mapper
public interface BoardDao {
	public int insert(Board board);
	public int update(Board board);
	public int delete(int bno);
	public Board selectByBno(int bno);
	public int countAllRow();
	public List<Board> selectPage(Pager pager);
}
