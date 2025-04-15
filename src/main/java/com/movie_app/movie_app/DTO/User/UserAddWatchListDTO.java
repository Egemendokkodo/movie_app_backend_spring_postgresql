package com.movie_app.movie_app.DTO.User;

import com.movie_app.movie_app.entity.MovieModels.Movie;

import lombok.Data;

@Data
public class UserAddWatchListDTO {
    Long userId;
    Movie movie;
}
