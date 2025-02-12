package com.vuestagram.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String name;
    private String userImage;
    private String postImage;
    private int likes;
    private String date;
    private boolean liked;
    private String content;
    private String filter;
}
