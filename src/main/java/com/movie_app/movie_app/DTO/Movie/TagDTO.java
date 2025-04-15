package com.movie_app.movie_app.DTO.Movie;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDTO {
    private String name;
    private Integer tagId;
}