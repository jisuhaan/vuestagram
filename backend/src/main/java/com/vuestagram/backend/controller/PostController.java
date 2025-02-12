package com.vuestagram.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vuestagram.backend.dto.PostDTO;
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
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    // 새 포스트 생성
    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        return postService.createPost(postDTO);
    }
}
