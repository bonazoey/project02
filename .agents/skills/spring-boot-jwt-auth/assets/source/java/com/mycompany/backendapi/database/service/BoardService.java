package com.mycompany.backendapi.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.backendapi.database.dao.BoardDao;
import com.mycompany.backendapi.database.dto.Pager;
import com.mycompany.backendapi.database.entity.Board;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	public int getTotalRows() {
		int rows = boardDao.countAllRow();
		return rows;
	}

	public List<Board> getList(Pager pager) {
		List<Board> boards = boardDao.selectPage(pager);
		return boards;
	}

	public Board create(Board board) {
		boardDao.insert(board);
		Board dbBoard = boardDao.selectByBno(board.getBno());
		return dbBoard;
	}

	public Board getBoard(int bno) {
		Board board = boardDao.selectByBno(bno);
		return board;
	}

	public Board update(Board board) {
		boardDao.update(board);
		Board dbBoard = boardDao.selectByBno(board.getBno());
		return dbBoard;
	}

	public boolean delete(int bno) {
		int rows = boardDao.delete(bno);
		if(rows == 1) return true;
		else return false;
	}
	
}







