package com.vuestagram.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vuestagram.backend.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
