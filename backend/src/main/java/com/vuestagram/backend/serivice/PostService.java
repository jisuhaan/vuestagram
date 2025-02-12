package com.vuestagram.backend.serivice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vuestagram.backend.entity.Post;
import com.vuestagram.backend.repository.PostRepository;

@Service
public class PostService {
	
	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<Post> getPosts() {
		// TODO Auto-generated method stub
		return null;
	}

}
