package com.movie_app.movie_app.DTO.Movie;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDetailsDTO {
    private Long movieId;
    private String description;
    private int movieLengthInMins;
    private String movieReleaseCountry;
    private long totalWatched;
    private double websiteRating;
    private String trailer;
}