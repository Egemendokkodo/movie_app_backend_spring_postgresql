package com.movie_app.movie_app.DTO.Movie;

import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class WatchOptionDTO {
    private String name;
    private Integer watchOptionId;
}
