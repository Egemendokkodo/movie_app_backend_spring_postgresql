package com.movie_app.movie_app.service.impl.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.movie_app.movie_app.DTO.Movie.MovieDTO;
import com.movie_app.movie_app.DTO.Movie.TagDTO;
import com.movie_app.movie_app.DTO.Movie.WatchOptionDTO;
import com.movie_app.movie_app.exception.TagNotFoundException;
import com.movie_app.movie_app.exception.WatchOptionNotFoundException;
import com.movie_app.movie_app.model.MovieModels.Movie;
import com.movie_app.movie_app.model.TagModels.Tag;
import com.movie_app.movie_app.model.WatchOptionModels.WatchOption;
import com.movie_app.movie_app.repository.Movie.MovieRepository;
import com.movie_app.movie_app.repository.Tag.TagRepository;
import com.movie_app.movie_app.repository.WatchOptions.WatchOptionRepository;
import com.movie_app.movie_app.service.Movie.MovieService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private TagRepository tagRepository;
    private WatchOptionRepository watchOptionRepository;

    public MovieServiceImpl(MovieRepository movieRepository,WatchOptionRepository watchOptionRepository,TagRepository tagRepository) {
        super();
        this.movieRepository = movieRepository;
        this.watchOptionRepository = watchOptionRepository;
        this.tagRepository = tagRepository;

    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    
    @Override
    public List<Movie> getMoviesByTagId(List<Integer> tagIds) {
        return movieRepository.findMoviesByTagIds(tagIds);
    }

    @Override
    public Movie getMovieById(long id) {

        return movieRepository.findById(id).get();
    }
    
    @Override
    public Boolean addMovie(MovieDTO movieDTO) {
        try {
            Movie movie = new Movie();
            movie.setName(movieDTO.getName());
            movie.setMovieReleaseYear(movieDTO.getMovieReleaseYear());
            movie.setMovieImdbRate(movieDTO.getMovieImdbRate());
            movie.setMovieImage(movieDTO.getMovieImage());
            movie.setType(movieDTO.getType());
    
            // Tagleri ekleme işlemi
            List<Tag> tags = new ArrayList<>();
            for (TagDTO tagDTO : movieDTO.getTags()) {
                Tag tag = tagRepository.findByName(tagDTO.getName())
                    .orElseThrow(() -> new TagNotFoundException("Tag not found: " + tagDTO.getName()));
                tags.add(tag);
            }
            movie.setTags(tags);
    
            // WatchOption'ları ekleme işlemi
            List<WatchOption> watchOptions = new ArrayList<>();
            for (WatchOptionDTO watchOptionDTO : movieDTO.getWatchOptions()) {
                WatchOption watchOption = watchOptionRepository.findByName(watchOptionDTO.getName())
                    .orElseThrow(() -> new WatchOptionNotFoundException("Watch Option not found: " + watchOptionDTO.getName()));
                watchOptions.add(watchOption);
            }
            movie.setWatchOptions(watchOptions);
    
            movieRepository.save(movie); // Son olarak filmi kaydet
            return true;
        } catch (TagNotFoundException | WatchOptionNotFoundException e) {
            throw e; // Rethrow custom exceptions
        } catch (Exception e) {
            throw new RuntimeException("Failed to add movie: " + e.getMessage());
        }
    }


    @Override
    public Boolean updateMovie(Long movieId, MovieDTO movieDTO) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            
            // Update only provided fields using Optional
            Optional.ofNullable(movieDTO.getName()).ifPresent(movie::setName);
            Optional.ofNullable(movieDTO.getMovieReleaseYear())
                    .filter(year -> year != 0) // Assuming a release year of 0 is invalid
                    .ifPresent(movie::setMovieReleaseYear);
            Optional.ofNullable(movieDTO.getMovieImdbRate())
                    .filter(rate -> rate != 0) // Assuming a rate of 0 is invalid
                    .ifPresent(movie::setMovieImdbRate);
            Optional.ofNullable(movieDTO.getMovieImage()).ifPresent(movie::setMovieImage);
            Optional.ofNullable(movieDTO.getType()).ifPresent(movie::setType);
            
            // Update tags if provided
            if (movieDTO.getTags() != null) {
                List<Tag> tags = movieDTO.getTags().stream()
                    .map(tagDTO -> tagRepository.findById(tagDTO.getTagId())
                            .orElseThrow(() -> new RuntimeException("Tag not found")))
                    .collect(Collectors.toList());
                movie.setTags(tags);
            }
    
            // Update watch options if provided
            if (movieDTO.getWatchOptions() != null) {
                List<WatchOption> watchOptions = movieDTO.getWatchOptions().stream()
                    .map(watchOptionDTO -> watchOptionRepository.findById(watchOptionDTO.getWatchOptionId())
                            .orElseThrow(() -> new RuntimeException("Watch option not found")))
                    .collect(Collectors.toList());
                movie.setWatchOptions(watchOptions);
            }
            
            movieRepository.save(movie);
            return true;
        } else {
            throw new RuntimeException("Movie not found");
        }
    }
    
    @Override
    public List<Tag> getMovieTagsByMovieId(long id) {
        // Fetch the movie by ID
        Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + id));
        

        return movie.getTags();
    }

    @Override
    public List<WatchOption> getMovieWatchOptionsByMovieId(long id) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + id));
        
        return movie.getWatchOptions();
    }

    @Override
    public Boolean deleteMovieById(Long movieId) {
        
        try {
            Movie movie=movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + movieId));

        movieRepository.delete(movie);
        return true;
        } catch (Exception e) {
            return false;
        }

        
    }

    


}
