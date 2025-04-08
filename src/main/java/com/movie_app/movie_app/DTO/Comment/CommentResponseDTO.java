package com.movie_app.movie_app.DTO.Comment;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
@Data
public class CommentResponseDTO {
    private Long id;
    private String username;
    private String content;
    private boolean containsSpoiler;
    private int likeCount;
    private int dislikeCount;
    private LocalDateTime createdAt;  

    private List<CommentResponseDTO> replies;

}