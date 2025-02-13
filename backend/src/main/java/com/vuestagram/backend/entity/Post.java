package com.vuestagram.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    private String name;
    private String userImage;
    private String postImage;
    private int likes;
    private String date;
    private boolean liked;
    private String content;
    private String filter;
    
 // id를 제외한 생성자
    public Post(String name, String userImage, String postImage, int likes, String date, boolean liked, String content, String filter) {
        this.name = name;
        this.userImage = userImage;
        this.postImage = postImage;
        this.likes = likes;
        this.date = date;
        this.liked = liked;
        this.content = content;
        this.filter = filter;
    }
}
