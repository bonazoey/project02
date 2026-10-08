package com.mycompany.backendapi.database.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.backendapi.database.dto.BoardCreateRequest;
import com.mycompany.backendapi.database.dto.BoardCreateResponse;
import com.mycompany.backendapi.database.dto.BoardDeleteResponse;
import com.mycompany.backendapi.database.dto.BoardListItemResponse;
import com.mycompany.backendapi.database.dto.BoardReadResponse;
import com.mycompany.backendapi.database.dto.BoardUpdateRequest;
import com.mycompany.backendapi.database.dto.BoardUpdateResponse;
import com.mycompany.backendapi.database.dto.Pager;
import com.mycompany.backendapi.database.entity.Board;
import com.mycompany.backendapi.database.interceptor.AccessTokenCheck;
import com.mycompany.backendapi.database.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/database/board")
@Slf4j
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/list")
	public Map<String, Object> list(@RequestParam(value="pageNo", defaultValue="1") int pageNo) {
		// 페이징 대상이 되는 전체 행의 수 얻기
		int totalRows = boardService.getTotalRows();
		// 페이징 정보를 담고 있는 Pager 생성
		Pager pager = new Pager(5, 5, totalRows, pageNo);
		// 현재 페이지 내용 가져오기
		List<Board> list = boardService.getList(pager);		
		// Board -> BoardListItemResponse 변환
		List<BoardListItemResponse> listItems = new ArrayList<>();
		for(Board board : list) {
			BoardListItemResponse item = new BoardListItemResponse();
			item.setBno(board.getBno());
			item.setBtitle(board.getBtitle());
			item.setBwriter(board.getBwriter());
			item.setBdate(board.getBdate());
			item.setBhitcount(board.getBhitcount());
			listItems.add(item);
		}		
		// JSON 응답을 위한 Map 리턴
		Map<String, Object> map = new HashMap<>();
		map.put("pager", pager);
		map.put("boards", listItems);
		return map;
	}
	
	@PostMapping("/create")
	@AccessTokenCheck
	public BoardCreateResponse create(
			@ModelAttribute BoardCreateRequest request,
			HttpServletRequest hsr) throws Exception {
		// Entity 생성
		Board board = new Board();
		
		// 문자 파트 확인
		log.info(request.getBtitle());
		log.info(request.getBcontent());
		board.setBtitle(request.getBtitle());
		board.setBcontent(request.getBcontent());
		board.setBwriter(hsr.getAttribute("mid").toString());
		
		// 파일 파트 확인 및 서버 파일 시스템에 파일을 저장
		MultipartFile mf = request.getBattach();
		if(mf != null && !mf.isEmpty()) {
			String battachoname = mf.getOriginalFilename();
			String contentType = mf.getContentType();
			log.info(battachoname);
			log.info(contentType);			
			board.setBattachoname(battachoname);
			board.setBattachtype(contentType);
			
			//방법1: DB에 파일을 직접 저장
			byte[] fileData = mf.getBytes();
			board.setBattachdata(fileData);
			
			//방법2: 서버 파일 시스템에 파일로 저장하고 관리
			/*String battachsname = new Date().getTime() + "-" + battachoname;
			log.info(battachsname);
			OutputStream os = new FileOutputStream("C:/Temp/" + battachsname);
			os.write(fileData);
			os.flush();
			os.close();	
			board.setBattachsname(battachsname);*/
		}
		
		// 서비스의 비즈니스 로직 호출
		Board dbBoard = boardService.create(board);
		
		//응답 생성
		BoardCreateResponse response = new BoardCreateResponse();
		response.setBno(dbBoard.getBno());
		response.setBtitle(dbBoard.getBtitle());
		response.setBwriter(dbBoard.getBwriter());
		response.setBdate(dbBoard.getBdate());
		response.setBattachoname(dbBoard.getBattachoname());
		response.setBattachtype(dbBoard.getBattachtype());		
		return response;
	}
	
	@GetMapping("/read/{bno}")
	@AccessTokenCheck
	public BoardReadResponse read(@PathVariable("bno") int bno) {
		// 서비스를 이용해서 bno에 대한 게시물 정보 가져오기
		Board dbBoard = boardService.getBoard(bno);
		
		// 응답 생성
		BoardReadResponse response = new BoardReadResponse();
		response.setBno(dbBoard.getBno());
		response.setBtitle(dbBoard.getBtitle());
		response.setBcontent(dbBoard.getBcontent());
		response.setBwriter(dbBoard.getBwriter());
		response.setBdate(dbBoard.getBdate());
		response.setBattachoname(dbBoard.getBattachoname());
		response.setBattachtype(dbBoard.getBattachtype());		
		response.setBattachsname(dbBoard.getBattachsname());	
		return response;
	}
	
	@GetMapping("/battach/{bno}")
	@AccessTokenCheck
	public void battach(@PathVariable("bno") int bno, HttpServletResponse response) throws Exception {
		// 서비스를 이용해서 bno에 대한 게시물 정보 가져오기
		Board dbBoard = boardService.getBoard(bno);
		
		// 직접 응답을 코드로 생성
		response.setContentType(dbBoard.getBattachtype());
		String fileName = dbBoard.getBattachoname();
		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		OutputStream os = response.getOutputStream();
		
		// 방법1(battachdata 이용)
		os.write(dbBoard.getBattachdata());
		os.flush();
		os.close();
		
		// 방법2(battchsname 이용)
		// Path path = Path.of("C:/Temp/" + dbBoard.getBattachsname());
		// Files.copy(path, os);
	}	
	
	@PutMapping("/update")
	@AccessTokenCheck
	public BoardUpdateResponse update(@ModelAttribute BoardUpdateRequest request) throws Exception {
		// Entity 생성
		Board board = new Board();
		
		// 문자 파트 확인
		board.setBtitle(request.getBtitle());
		board.setBcontent(request.getBcontent());
		board.setBno(request.getBno());
		
		// 파일 파트 확인 및 서버 파일 시스템에 파일을 저장
		MultipartFile mf = request.getBattach();
		if(mf != null && !mf.isEmpty()) {
			String battachoname = mf.getOriginalFilename();
			String contentType = mf.getContentType();			
			board.setBattachoname(battachoname);
			board.setBattachtype(contentType);
			
			//방법1: DB에 파일을 직접 저장
			byte[] fileData = mf.getBytes();
			board.setBattachdata(fileData);
			
			//방법2: 서버 파일 시스템에 파일로 저장하고 관리
			/*String battachsname = new Date().getTime() + "-" + battachoname;
			log.info(battachsname);
			OutputStream os = new FileOutputStream("C:/Temp/" + battachsname);
			os.write(fileData);
			os.flush();
			os.close();	
			board.setBattachsname(battachsname);*/
		}
		
		// 서비스의 비즈니스 로직 호출
		Board dbBoard = boardService.update(board);
		
		//응답 생성
		BoardUpdateResponse response = new BoardUpdateResponse();
		response.setBno(dbBoard.getBno());
		response.setBtitle(dbBoard.getBtitle());
		response.setBwriter(dbBoard.getBwriter());
		response.setBdate(dbBoard.getBdate());
		response.setBattachoname(dbBoard.getBattachoname());
		response.setBattachtype(dbBoard.getBattachtype());		
		return response;
	}
	
	@DeleteMapping("/delete/{bno}")
	@AccessTokenCheck
	public BoardDeleteResponse delete(@PathVariable("bno") int bno) {
		boolean result = boardService.delete(bno);
		
		BoardDeleteResponse response = new BoardDeleteResponse();
		if(result) {
			response.setResult("success");
		} else {
			response.setResult("failure");
			response.setMessage("bno가 존재하지 않습니다.");
		}
		return response;
	}
	

}













