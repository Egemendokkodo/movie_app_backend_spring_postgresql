package com.movie_app.movie_app.controller.MovieControllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie_app.movie_app.DTO.Movie.DeleteMovieRequest;
import com.movie_app.movie_app.DTO.Movie.MovieDTO;
import com.movie_app.movie_app.message.ReturnMessageFromApi;
import com.movie_app.movie_app.model.MovieModels.Movie;
import com.movie_app.movie_app.service.Movie.MovieService;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        super();
        this.movieService = movieService;
    }

    @GetMapping(path = "/get-all-movies")
    public ResponseEntity<Map<String, Object>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        Page<Movie> movies = movieService.getAllMovies(page, size);
        Map<String, Object> response = new HashMap<>();
        response.put("movies", movies.getContent()); // Filmler
        response.put("currentPage", movies.getNumber()); // Mevcut sayfa
        response.put("totalItems", movies.getTotalElements()); // Toplam film sayısı
        response.put("totalPages", movies.getTotalPages()); // Toplam sayfa sayısı
        return ReturnMessageFromApi.returnMessageOnSuccess(true, "Movies fetched successfully.", HttpStatus.OK,
                response);
    }

    @PostMapping("/get-movies-by-tag-id")
    public ResponseEntity<Map<String, Object>> getMoviesByTagId(
            @RequestBody List<Integer> tagIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<Movie> moviesPage = movieService.getMoviesByTagId(tagIds, page, size);
        List<Movie> movies = moviesPage.getContent();

        if (!movies.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("movies", movies);
            response.put("currentPage", moviesPage.getNumber());
            response.put("totalItems", moviesPage.getTotalElements());
            response.put("totalPages", moviesPage.getTotalPages());

            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Movies fetched successfully", HttpStatus.OK,
                    response);
        } else {
            return ReturnMessageFromApi.returnMessageOnError(false, "Movies not found with id list:" + tagIds,
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add-movie")
    public ResponseEntity<Map<String, Object>> addMovie(@RequestBody MovieDTO movieDTO) {
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
            final Movie response = movieService.getMovieById(id);
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

    @PostMapping("/delete-movie-by-id")
    public ResponseEntity<Map<String, Object>> postMethodName(@RequestBody DeleteMovieRequest request) {

        final Boolean success = movieService.deleteMovieById(request.getId());
        if (Boolean.TRUE.equals(success)) {
            return ReturnMessageFromApi.returnMessageOnError(true, "Successfully deleted movie",
                    HttpStatus.OK);
        } else {
            return ReturnMessageFromApi.returnMessageOnError(false, "Failed to delete movie",
                    HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-movie-by-year/{year}")
    public ResponseEntity<Map<String, Object>> getMoviesByYear(@PathVariable int year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size) {
        try {
            Page<Movie> movies= movieService.getMoviesByYear(page, size, year);
            Map<String, Object> response = new HashMap<>();
        response.put("movies", movies.getContent()); // Filmler
        response.put("currentPage", movies.getNumber()); // Mevcut sayfa
        response.put("totalItems", movies.getTotalElements()); // Toplam film sayısı
        response.put("totalPages", movies.getTotalPages()); // Toplam sayfa sayısı
            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Successfully fetched movies",
                    HttpStatus.OK,response);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(false, "Failed to fetch movies by year",
            HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/{movieId}/watch")
    public ResponseEntity<Boolean> watchMovie(
            @PathVariable Long movieId,
            @RequestParam Long userId) {
        
        boolean incremented = movieService.incrementMovieWatchCount(userId, movieId);
        
        if (incremented) {
            return ResponseEntity.ok(true); // HTTP 200 OK ve true değeri
        } else {
            return ResponseEntity.ok(false); // HTTP 200 OK ve false değeri
        }
    }
    
}


