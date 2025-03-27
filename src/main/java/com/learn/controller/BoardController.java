package com.learn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.learn.service.BoardService;
import com.learn.vo.BoardVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	// get방식은 query string으로 받음
	@GetMapping
	public Map<String, Object> getBoards(@RequestParam Map<String, Object> params, HttpSession session) {
		log.info("*** getBoards Call! ***");
		
		session.setAttribute("userId", "test");
		String userId = (String)session.getAttribute("userId");
		log.info("#########getBoards.userId.log ######"+userId);
		
		Map<String, Object> result = new HashMap<>();

		List<BoardVO> content = new ArrayList<>();
		content = boardService.getBoards();

//      Map<String, Object> map1 = new HashMap<>();
//      map1.put("content", params.get("searchKeyWord"));
//      map1.put("writer", params.get("name"));
//      map1.put("title", params.get("title"));
//      map1.put("content", "test Content1 ");
//      map1.put("writer", "test Writer1");
//      map1.put("title", "test Title1");
//      map1.put("id", 1);
//
//      Map<String, Object> map2 = new HashMap<>();
//      map2.put("content", "test Content2 ");
//      map2.put("writer", "test Writer2");
//      map2.put("title", "test Title2");
//      map2.put("id", 2);
//
//      content.add(map1);
//      content.add(map2);
//		DB 설정 다 돌아가는거 확인하면 그떄 마지막으로 지운다. 더미데이터  
//
		int totalPages = (int) Math.floor(content.size() / 10.0);
		result.put("content", content);
		result.put("totalPages", totalPages);

		return result;
	}

	@GetMapping("/{id}")
	public BoardVO getBoardById(@PathVariable Long id) {
		BoardVO result = null;

//      result.put("content", "content text");
//      result.put("writer", "writer kim");
//      result.put("title", "test title " + id);
//      result.put("createdAt", "2025/05/05");
//      result.put("views", 13);
//      result.put("id", id);

		result = boardService.getBoardById(id);

		return result;
	}

	@PostMapping
	public Map<String, Object> createBoard(MultipartHttpServletRequest req, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		List<MultipartFile> files = new ArrayList<>();
		
		String userId = (String)session.getAttribute("userId");
		log.info("#########createBoard.userId.log ######"+userId);
		
		String writer = req.getParameter("writer");
		String title = req.getParameter("title");
		String content = req.getParameter("content");

		BoardVO boardVO = new BoardVO();
		boardVO.setWriter(writer);
		boardVO.setTitle(title);
		boardVO.setContent(content);
		boardVO.setCreatedId("writerId");
		// 세션에서 취득 (서버에서 가지고 있는 사용자의개인정보) 접속한 사람들 메모리에 보관.
		// 세션에 로그인하면 기록이 남는데 그걸사용한다.=> 로그인할때.
		// 서버가 로그인 정보를 거치면서 자기 메모리에 챙겨둔 메모리.
		// 로그인 로그아웃 부분 .

		for (int i = 0; i < req.getFiles("file").size(); i++) {
			files.add(req.getFiles("file").get(i));
		}

		result = boardService.createBoard(boardVO, files);
		session.invalidate();
		
		return result;
	}
}