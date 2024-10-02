package com.movie_app.movie_app.service.Movie;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.movie_app.movie_app.DTO.Movie.MovieDTO;
import com.movie_app.movie_app.model.MovieModels.Movie;
import com.movie_app.movie_app.model.TagModels.Tag;
import com.movie_app.movie_app.model.WatchOptionModels.WatchOption;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(long id);
    List<Movie> getMoviesByTagId(List<Integer> tagIds);
    Boolean addMovie(MovieDTO movie);
    List<Tag> getMovieTagsByMovieId(long id);
    List<WatchOption> getMovieWatchOptionsByMovieId(long id);
    Boolean deleteMovieById(Long movieId);
    Boolean updateMovie(Long movieId, MovieDTO movieDTO);

}
