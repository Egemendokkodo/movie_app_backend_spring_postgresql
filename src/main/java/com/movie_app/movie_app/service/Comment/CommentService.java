package com.movie_app.movie_app.service.Comment;

import java.util.List;

import com.movie_app.movie_app.DTO.Comment.CommentRequestDTO;
import com.movie_app.movie_app.DTO.Comment.CommentResponseDTO;
import com.movie_app.movie_app.entity.Comment.Comment;

public interface CommentService {
     public Comment addComment(CommentRequestDTO dto);
     public List<CommentResponseDTO> getCommentsForMovie(Long movieId);
     public CommentResponseDTO convertToDTO(Comment comment);
     void likeComment(Long commentId, String username);
     void dislikeComment(Long commentId, String username);


}
