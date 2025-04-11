package com.movie_app.movie_app.controller.Comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie_app.movie_app.DTO.Comment.CommentRequestDTO;
import com.movie_app.movie_app.DTO.Comment.CommentResponseDTO;
import com.movie_app.movie_app.entity.Comment.Comment;
import com.movie_app.movie_app.service.Comment.CommentService;
import com.movie_app.movie_app.service.Movie.MovieService;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;

    // Constructor Injection
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequestDTO dto) {
        return ResponseEntity.ok(commentService.addComment(dto));
    }

    @PostMapping("/{parentId}/reply")
    public ResponseEntity<Comment> replyToComment(@PathVariable Long parentId,
            @RequestBody CommentRequestDTO dto) {
        dto.setParentId(parentId);
        return ResponseEntity.ok(commentService.addComment(dto));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long movieId) {
        return ResponseEntity.ok(commentService.getCommentsForMovie(movieId));
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<Void> likeComment(@PathVariable Long commentId, @RequestParam String username) {
        commentService.likeComment(commentId, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commentId}/dislike")
    public ResponseEntity<Void> dislikeComment(@PathVariable Long commentId, @RequestParam String username) {
        commentService.dislikeComment(commentId, username);
        return ResponseEntity.ok().build();
    }

}