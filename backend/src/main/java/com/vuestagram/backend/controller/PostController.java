package com.vuestagram.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vuestagram.backend.dto.PostDTO;
import com.vuestagram.backend.entity.Post;
import com.vuestagram.backend.serivice.PostService;

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

	@GetMapping("/more")
    public List<Post> getPosts(@RequestParam String filter, @RequestParam int filter) {
        return postService.getPosts();  // 포스트 리스트를 반환
    }
}
