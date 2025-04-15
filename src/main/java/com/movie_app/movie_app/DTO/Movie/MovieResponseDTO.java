package com.movie_app.movie_app.DTO.Movie;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieResponseDTO {
    private Long movieId;
    private String type;
    private String name;
    private String movieImage;
    private int movieReleaseYear;
    private double movieImdbRate;
    private long movieTotalCommentCount;
    private List<TagDTO> tags;
    private List<WatchOptionDTO> watchOptions;
    private MovieDetailsDTO movieDetails;
}
