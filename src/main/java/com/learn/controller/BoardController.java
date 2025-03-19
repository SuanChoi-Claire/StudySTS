package com.learn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	
	@GetMapping
	public Map<String, Object> getBoards(){
		Map<String,Object> result = new HashMap<>();
		List<Map<String, Object>> content = new ArrayList<>();
		
		Map<String, Object> map1 = new HashMap<>();
		map1.put("content", "test content1 " );
		map1.put("writer","Writer name");
		map1.put("title","title test ");
		map1.put("id", 1);
		
		Map<String, Object> map2 = new HashMap<>();
		map2.put("content", "test content2");
		map2.put("writer", "Writer name2");
		map2.put("title", "title test2");
		map2.put("id", 2);
		
		content.add(map1);
		content.add(map2);
		
		result.put("content", content);
		result.put("totalPages", 10 );
		
		// result ={"totalpages":1,"content":[{"writer":"Writer name","id":2,"title":"title test ","content":"test content1 "},{"writer":"Writer name2","title":"title test2","content":"test content2"}]}
		
		
		
		return result;
	} 
	
	
	@GetMapping("/{id}")
	public Map<String, Object> getBoardById(@PathVariable long id){
		Map<String, Object> result = new HashMap<>();
		result.put("content", "content test");
		result.put("writer", "write test");
		result.put("title", "title test" + id);
		result.put("createdAt", "createAt 2025/05/05"+ id );
		result.put("views", 13);
		result.put("id", id);
		return result;
	}
	
	
	
	

}
