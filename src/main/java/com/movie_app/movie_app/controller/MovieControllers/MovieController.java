package com.movie_app.movie_app.controller.MovieControllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.movie_app.movie_app.DTO.Movie.DeleteMovieRequest;
import com.movie_app.movie_app.DTO.Movie.MovieDTO;
import com.movie_app.movie_app.message.ReturnMessageFromApi;
import com.movie_app.movie_app.model.MovieModels.Movie;
import com.movie_app.movie_app.service.Movie.MovieService;
import com.movie_app.movie_app.service.WatchOption.WatchOptionService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/movie")
@CrossOrigin
public class MovieController {

    private MovieService movieService;
    private WatchOptionService watchOptionService;

    public MovieController(MovieService movieService, WatchOptionService watchOptionService) {
        super();
        this.movieService = movieService;
        this.watchOptionService = watchOptionService;
    }

    @GetMapping(path = "/get-all-movies")
    public ResponseEntity<Map<String, Object>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ReturnMessageFromApi.returnMessageOnSuccess(true, "true", HttpStatus.OK, movies);
    }

    @PostMapping("/get-movies-by-tag-id")
    public ResponseEntity<Map<String, Object>> getMoviesByTagId(@RequestBody List<Integer> tagIds) {
        List<Movie> movies = movieService.getMoviesByTagId(tagIds);
        if (!movies.isEmpty()) {
            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Movies fetched successfully", HttpStatus.OK,
                    movies);
        } else {
            return ReturnMessageFromApi.returnMessageOnError(false, "Movies not found with id list:" + tagIds,
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add-movie")
    public ResponseEntity<Map<String, Object>> addMovie(@RequestBody MovieDTO movieDTO) {
        /*
         * Boolean success = movieService.addMovie(movieDTO);
         * if (Boolean.TRUE.equals(success)) {
         * return ReturnMessageFromApi.returnMessageOnSuccess(true,
         * "Movie added successfully", HttpStatus.CREATED, true);
         * } else {
         * return ReturnMessageFromApi.returnMessageOnError(false,
         * "Failed to add movie", HttpStatus.INTERNAL_SERVER_ERROR);
         * }
         */
        try {
            movieService.addMovie(movieDTO);
            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Movie added successfully", HttpStatus.CREATED,
                    true);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(false, "Failed to add movie: " + e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-movie-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getMovieById(@PathVariable Long id) {
        try {
           final Movie response= movieService.getMovieById(id);
            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Movie fetced successfully", HttpStatus.CREATED,
            response);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(false, "Failed to fetch movie: " + e,
                    HttpStatus.NOT_FOUND);
        }
    }
    

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        try {
            movieService.updateMovie(id, movieDTO);
            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Movie updated successfully", HttpStatus.OK, true);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(false, "Failed to update movie: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
