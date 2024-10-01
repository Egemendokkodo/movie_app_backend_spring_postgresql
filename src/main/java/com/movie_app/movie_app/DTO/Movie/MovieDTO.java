package com.movie_app.movie_app.DTO.Movie;

import java.util.List;

import com.movie_app.movie_app.utils.MovieTypeEnum;

import lombok.Data;

@Data
public class MovieDTO {
    private String name;
    private String movieImage;
    private int movieReleaseYear;
    private double movieImdbRate;
    private MovieTypeEnum type;
    private List<TagDTO> tags; // Tag için DTO
    private List<WatchOptionDTO> watchOptions; // WatchOption için DTO
}
