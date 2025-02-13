package com.vuestagram.backend.dto;

import java.util.List;

import com.vuestagram.backend.config.ApiStatus;
import com.vuestagram.backend.dto.PostDTO;

import lombok.Getter;

@Getter
public class PostsResponseDTO extends ResponseDTO<List<PostDTO>> {

    public PostsResponseDTO(List<PostDTO> body) {
        super(ApiStatus.SUCCESS, true, body);
    }
}