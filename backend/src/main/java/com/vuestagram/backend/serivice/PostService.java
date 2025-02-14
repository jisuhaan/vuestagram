package com.vuestagram.backend.serivice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vuestagram.backend.dto.PostDTO;
import com.vuestagram.backend.entity.Post;
import com.vuestagram.backend.repository.PostRepository;

import jakarta.annotation.PostConstruct;

@Service
public class PostService {
	
	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@PostConstruct
    public void init() {
		postRepository.deleteAll();
        // 테스트용 데이터 삽입
		Post post1 = new Post("Kim Hyun", "https://picsum.photos/100?random=3", "https://picsum.photos/600?random=3", 36, "May 15", false, "오늘 무엇을 했냐면요 아무것도 안했어요 ?", "perpetua");
        Post post2 = new Post("John Doe", "https://picsum.photos/100?random=4", "https://picsum.photos/600?random=4", 20, "Apr 20", false, "흔한 자랑스타그램", "clarendon");
        Post post3 = new Post("Minny", "https://picsum.photos/100?random=5", "https://picsum.photos/600?random=5", 49, "Apr 4", false, "우리집 개는 화장실 물도 내림", "lofi");
      
        // DB에 저장
        postRepository.saveAll(Arrays.asList(post1, post2, post3));
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
