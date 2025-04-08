package com.movie_app.movie_app.repository.Comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie_app.movie_app.entity.Comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMovie_MovieIdAndParentIsNull(Long movieId);

    long countByMovie_MovieId(Long movieId);



}