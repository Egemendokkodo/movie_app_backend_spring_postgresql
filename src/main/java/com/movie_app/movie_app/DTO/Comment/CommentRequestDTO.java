package com.movie_app.movie_app.DTO.Comment;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private String username;
    private String content;
    private boolean containsSpoiler;
    private Long movieId;
    private Long parentId; // null ise ana yorumdur
}