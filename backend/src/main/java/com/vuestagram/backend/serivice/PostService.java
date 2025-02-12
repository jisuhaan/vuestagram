package com.vuestagram.backend.serivice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vuestagram.backend.dto.PostDTO;
import com.vuestagram.backend.entity.Post;
import com.vuestagram.backend.repository.PostRepository;

@Service
public class PostService {
	
	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	
	// 모든 포스트 가져오기
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    
    // 새 포스트 저장
    public PostDTO createPost(PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    // DTO -> Entity 변환
    private Post convertToEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setName(postDTO.getName());
        post.setUserImage(postDTO.getUserImage());
        post.setPostImage(postDTO.getPostImage());
        post.setLikes(postDTO.getLikes());
        post.setDate(postDTO.getDate());
        post.setLiked(postDTO.isLiked());
        post.setContent(postDTO.getContent());
        post.setFilter(postDTO.getFilter());
        return post;
    }

    // Entity -> DTO 변환
    private PostDTO convertToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setName(post.getName());
        postDTO.setUserImage(post.getUserImage());
        postDTO.setPostImage(post.getPostImage());
        postDTO.setLikes(post.getLikes());
        postDTO.setDate(post.getDate());
        postDTO.setLiked(post.isLiked());
        postDTO.setContent(post.getContent());
        postDTO.setFilter(post.getFilter());
        return postDTO;
    }

}
