package com.movie_app.movie_app.repository.Movie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie_app.movie_app.entity.MovieModels.MovieDetails;

public interface MovieDetailsRepository extends JpaRepository<MovieDetails, Long> {
    
}
