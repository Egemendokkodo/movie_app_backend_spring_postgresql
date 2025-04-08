package com.movie_app.movie_app.service.impl.Comment;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie_app.movie_app.DTO.Comment.CommentRequestDTO;
import com.movie_app.movie_app.DTO.Comment.CommentResponseDTO;
import com.movie_app.movie_app.entity.Comment.Comment;
import com.movie_app.movie_app.entity.MovieModels.Movie;
import com.movie_app.movie_app.repository.Comment.CommentRepository;
import com.movie_app.movie_app.repository.Movie.MovieRepository;
import com.movie_app.movie_app.service.Comment.CommentService;
@Service
public class CommentServiceImpl implements CommentService {

     @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MovieRepository movieRepository;


    @Override
    public Comment addComment(CommentRequestDTO dto) {
               Comment comment = new Comment();
        comment.setUsername(dto.getUsername());
        comment.setContent(dto.getContent());
        comment.setContainsSpoiler(dto.isContainsSpoiler());

        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        comment.setMovie(movie);

        if (dto.getParentId() != null) {
            Comment parent = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParent(parent);
        }

        return commentRepository.save(comment);
    }

    @Override
    public List<CommentResponseDTO> getCommentsForMovie(Long movieId) {
         List<Comment> comments = commentRepository.findByMovie_MovieIdAndParentIsNull(movieId);
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDTO convertToDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setUsername(comment.getUsername());
        dto.setContent(comment.getContent());
        dto.setContainsSpoiler(comment.isContainsSpoiler());
        dto.setLikeCount(comment.getLikeCount());
        dto.setDislikeCount(comment.getDislikeCount());
        dto.setCreatedAt(comment.getCreatedAt());  // Burada createdAt değeri atanmalı
    
        List<CommentResponseDTO> replies = comment.getReplies().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        dto.setReplies(replies);
    
        return dto;
    }
    
    
}
