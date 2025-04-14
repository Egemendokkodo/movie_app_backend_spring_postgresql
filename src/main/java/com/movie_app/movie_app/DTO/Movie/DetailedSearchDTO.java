package com.movie_app.movie_app.DTO.Movie;

import java.util.List;

import lombok.Data;

@Data
public class DetailedSearchDTO {
    String watchType;
    List<Integer> tagIds;
    Integer yearOfMovie;
    Integer imdbScore;
    String searchFilter;
}
