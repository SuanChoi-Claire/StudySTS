package com.learn.service;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.learn.mapper.BoardMapper;
import com.learn.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {

	@Autowired
	private BoardMapper boardMapper;
	private final Path uploadPath = Path.of(System.getProperty("user.dir"), "uploads");

//	private static final BoardMapper boardMapper;
//	
//	BoardService(BoardMapper boardMapper){
//		this.boardMapper = boardMapper;
//		
//	} 원래 이렇게 해야하는데 @Autowired  쓰면 대신해줘서 코드가 간단해진다.

	public List<BoardVO> getBoards() {
		List<BoardVO> result = null;
		result = boardMapper.getBoards();
		return result;
	}

	public BoardVO getBoardById(Long id) {
		BoardVO result = null;
		result = boardMapper.getBoardById(id);
		return result;
	}

	public Map<String, Object> createBoard(BoardVO boardVO, List<MultipartFile> files) {
		Map<String, Object> result = new HashMap<>();
		int createcnt = boardMapper.createBoard(boardVO);
		result.put("result", createcnt);

		if (files != null && !files.isEmpty()) {
			for (MultipartFile file : files) {
				String randomUUID = UUID.randomUUID().toString();
				String originalFileName = file.getOriginalFilename();
				result.put(originalFileName, randomUUID);
				String Extension = originalFileName.substring(originalFileName.lastIndexOf("."));
				log.debug(randomUUID, "|", originalFileName, "|", Extension);
				log.info("writer", boardVO.getWriter());
				String saveFileName = randomUUID + Extension;

				Path target = uploadPath.resolve(saveFileName);

				try {
					// target에 파일 업로드 한다.
					file.transferTo(target);
				} catch (IllegalStateException e) {
//	               e.printStackTrace(); //운영때는 하면 안됨
					log.error("createBoard IllegalStateException raise!!!" + e.getMessage());
				} catch (IOException e) {
					log.error("createBoard IOException raise!!!" + e.getMessage());
				} catch (Exception e) {
					log.error("createBoard Exception raise!!!" + e.getMessage());
				}
			}
		}

		return result;
	}

}
