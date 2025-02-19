package com.vuestagram.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vuestagram.backend.dto.PostDTO;
import com.vuestagram.backend.dto.PostsResponseDTO;
import com.vuestagram.backend.dto.ResponseDTO;
import com.vuestagram.backend.entity.Post;
import com.vuestagram.backend.serivice.PostService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	// 1. 필드 주입방식 -> 자동 객체 주입
//	@Autowired
//	private PostService postService;
	
	// 2. 생성자 주입 방식 -> 객체의 불변성 보장하므로 이쪽을 더 권장
	private final PostService postService; 
		
	public PostController (PostService postService) {
		this.postService = postService;
	}
	
	 // 모든 포스트 가져오기
	@GetMapping("/more")
    public ResponseEntity<PostsResponseDTO> getAllPosts(
            @RequestParam(name = "offset", defaultValue = "0") int offset,  // 기본값 0
            @RequestParam(name = "limit", defaultValue = "3") int limit) { // 기본값 3
		// name 밝혀주기!
        List<PostDTO> posts = postService.getPosts(offset, limit);  // 서비스에서 페이징 처리된 데이터를 가져옴
        return ResponseEntity.ok(new PostsResponseDTO(posts));  // 응답
    }

    // 새 포스트 생성
    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        return postService.createPost(postDTO);
    }
}
