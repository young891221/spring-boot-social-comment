package com.social.repository;

import com.social.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by KimYJ on 2017-06-08.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
