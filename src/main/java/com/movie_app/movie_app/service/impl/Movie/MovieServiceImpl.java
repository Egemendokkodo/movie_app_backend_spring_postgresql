package com.movie_app.movie_app.service.impl.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.movie_app.movie_app.DTO.Movie.MovieDTO;
import com.movie_app.movie_app.DTO.Movie.TagDTO;
import com.movie_app.movie_app.DTO.Movie.WatchOptionDTO;
import com.movie_app.movie_app.exception.TagNotFoundException;
import com.movie_app.movie_app.exception.WatchOptionNotFoundException;
import com.movie_app.movie_app.model.MovieModels.Movie;
import com.movie_app.movie_app.model.MovieModels.MovieDetails;
import com.movie_app.movie_app.model.TagModels.Tag;
import com.movie_app.movie_app.model.WatchOptionModels.WatchOption;
import com.movie_app.movie_app.repository.Movie.MovieDetailsRepository;
import com.movie_app.movie_app.repository.Movie.MovieRepository;
import com.movie_app.movie_app.repository.Tag.TagRepository;
import com.movie_app.movie_app.repository.WatchOptions.WatchOptionRepository;
import com.movie_app.movie_app.service.Movie.MovieService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private TagRepository tagRepository;
    private WatchOptionRepository watchOptionRepository;

    private MovieDetailsRepository movieDetailsRepository;

    public MovieServiceImpl(MovieRepository movieRepository, WatchOptionRepository watchOptionRepository,
            TagRepository tagRepository,MovieDetailsRepository movieDetailsRepository) {
        super();
        this.movieRepository = movieRepository;
        this.watchOptionRepository = watchOptionRepository;
        this.tagRepository = tagRepository;
        this.movieDetailsRepository=movieDetailsRepository;

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
            

            List<Tag> tags = new ArrayList<>();
            for (TagDTO tagDTO : movieDTO.getTags()) {
                Tag tag = tagRepository.findByName(tagDTO.getName())
                        .orElseThrow(() -> new TagNotFoundException("Tag not found: " + tagDTO.getName()));
                tags.add(tag);
            }
            movie.setTags(tags);

            List<WatchOption> watchOptions = new ArrayList<>();
            for (WatchOptionDTO watchOptionDTO : movieDTO.getWatchOptions()) {
                WatchOption watchOption = watchOptionRepository.findByName(watchOptionDTO.getName())
                        .orElseThrow(() -> new WatchOptionNotFoundException(
                                "Watch Option not found: " + watchOptionDTO.getName()));
                watchOptions.add(watchOption);
            }
            movie.setWatchOptions(watchOptions);
            Movie savedMovie = movieRepository.save(movie);
            if (movieDTO.getMovieDetails() != null) {
                MovieDetails movieDetails = new MovieDetails();
                movieDetails.setMovie(savedMovie);  // Kaydedilen Movie'nin ID'si kullanılıyor
                movieDetails.setDescription(movieDTO.getMovieDetails().getDescription());
                movieDetails.setMovieLengthInMins(movieDTO.getMovieDetails().getMovieLengthInMins());
                movieDetails.setMovieReleaseCountry(movieDTO.getMovieDetails().getMovieReleaseCountry());
                movieDetails.setTotalWatched(movieDTO.getMovieDetails().getTotalWatched());
                movieDetails.setWebsiteRating(movieDTO.getMovieDetails().getWebsiteRating());
                movieDetails.setTrailer(movieDTO.getMovieDetails().getTrailer());
    
                movieDetailsRepository.save(movieDetails);  // MovieDetails kaydediliyor
            }

            
            return true;
        } catch (TagNotFoundException | WatchOptionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("asd"+e.getMessage());
        }
    }

    @Override
    public Boolean updateMovie(Long movieId, MovieDTO movieDTO) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();

            Optional.ofNullable(movieDTO.getName()).ifPresent(movie::setName);
            Optional.ofNullable(movieDTO.getMovieReleaseYear())
                    .filter(year -> year != 0)
                    .ifPresent(movie::setMovieReleaseYear);
            Optional.ofNullable(movieDTO.getMovieImdbRate())
                    .filter(rate -> rate != 0)
                    .ifPresent(movie::setMovieImdbRate);
            Optional.ofNullable(movieDTO.getMovieImage()).ifPresent(movie::setMovieImage);
            Optional.ofNullable(movieDTO.getType()).ifPresent(movie::setType);

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
            throw runtimeException();
        }
    }

    @Override
    public List<Tag> getMovieTagsByMovieId(long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> entityNotFoundException(id));

        return movie.getTags();
    }

    @Override
    public List<WatchOption> getMovieWatchOptionsByMovieId(long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> entityNotFoundException(id));

        return movie.getWatchOptions();
    }

    @Override
    public Boolean deleteMovieById(Long movieId) {

        try {
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + movieId));

            movieRepository.delete(movie);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private EntityNotFoundException entityNotFoundException(long id) {
        return new EntityNotFoundException("Movie not found with id: " + id);
    }

    private RuntimeException runtimeException() {
        return new EntityNotFoundException("Movie not found ");
    }

}
