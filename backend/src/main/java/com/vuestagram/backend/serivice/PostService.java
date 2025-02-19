package com.vuestagram.backend.serivice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
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
    public List<PostDTO> getPosts(int offset, int limit) { // 무한 스크롤 방식이어도 페이징처리하는 게 서버 성능에 적절
    	
    	Pageable pageable = PageRequest.of(offset / limit, limit);
    	Page<Post> posts = postRepository.findAll(pageable); // 페이지 객체로 결과 가져오기
    	
    	if (posts.isEmpty()) {
            // 데이터가 없으면 더미 데이터를 반환 (여기서 count를 설정)
            List<PostDTO> dummyPosts = generateDummyPostsDTO(3); // 5개의 더미 데이터를 반환
            return dummyPosts;
        }
    	
        List<PostDTO> postDTOs = posts.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return postDTOs;
        // 혹은 총 페이지 수, 총 요소 수 포함한 응답 객체 반환하는 것도 방법!
//        return new PostsResponseDTO(postDTOs, postPage.getTotalElements(), postPage.getTotalPages()); 
    }
    
    // 더미 데이터 생성 메서드 : 단일 책임의 원칙 따라 분리
    private List<Post> generateDummyPosts(int count) {
        List<Post> dummyPosts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Post dummyPost = new Post();
            dummyPost.setName("User " + (i + 1));
            dummyPost.setUserImage("https://picsum.photos/100?random=" + (Math.random() * 1000));
            dummyPost.setPostImage("https://picsum.photos/400/300?random=" + (Math.random() * 1000));
            dummyPost.setLikes((int) (Math.random() * 1000));
            dummyPost.setDate("2025-02-19");
            dummyPost.setLiked(Math.random() > 0.5); // 랜덤으로 좋아요 여부
            dummyPost.setContent("This is a dummy post #" + (i + 1));
            dummyPost.setFilter("all");

            dummyPosts.add(dummyPost);
        }
        return dummyPosts;
    }

    // 더미 데이터를 DTO로 반환하는 메서드
    private List<PostDTO> generateDummyPostsDTO(int count) {
        return generateDummyPosts(count).stream()
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
