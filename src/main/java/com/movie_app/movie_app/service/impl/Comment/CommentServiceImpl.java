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

import jakarta.transaction.Transactional;
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
        
    
        Comment saved = commentRepository.save(comment);
    
       
        long count = commentRepository.countByMovie_MovieId(dto.getMovieId());
        movie.setMovieTotalCommentCount(count);
        movieRepository.save(movie);
    
        return saved;
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
        dto.setCreatedAt(comment.getCreatedAt());  
    
        List<CommentResponseDTO> replies = comment.getReplies().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        dto.setReplies(replies);
    
        return dto;
    }


    public void likeComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı"));

        if (comment.getLikedBy().contains(username)) {
  
            comment.getLikedBy().remove(username);
            comment.setLikeCount(comment.getLikeCount() - 1);
        } else {
            
            comment.getLikedBy().add(username);
            comment.setLikeCount(comment.getLikeCount() + 1);
    
            if (comment.getDislikedBy().contains(username)) {
                comment.getDislikedBy().remove(username);
                comment.setDislikeCount(comment.getDislikeCount() - 1);
            }
        }

        commentRepository.save(comment);
    }

    public void dislikeComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı"));

        if (comment.getDislikedBy().contains(username)) {
           
            comment.getDislikedBy().remove(username);
            comment.setDislikeCount(comment.getDislikeCount() - 1);
        } else {
        
            comment.getDislikedBy().add(username);
            comment.setDislikeCount(comment.getDislikeCount() + 1);
           
            if (comment.getLikedBy().contains(username)) {
                comment.getLikedBy().remove(username);
                comment.setLikeCount(comment.getLikeCount() - 1);
            }
        }

        commentRepository.save(comment);
    }
    
    
}
