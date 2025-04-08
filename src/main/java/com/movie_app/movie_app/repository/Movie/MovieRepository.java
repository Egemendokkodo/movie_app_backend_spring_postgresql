package com.movie_app.movie_app.repository.Movie;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movie_app.movie_app.entity.MovieModels.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    
    @Query("SELECT m FROM Movie m JOIN m.tags t WHERE t.tagId IN :tagIds ")
    Page<Movie> findMoviesByTagIds(@Param("tagIds") List<Integer> tagIds, Pageable pageable);


    @Query("SELECT m FROM Movie m WHERE m.movieReleaseYear = :year")
    Page<Movie> findMoviesByYear(@Param("year") int year, Pageable pageable);
    
}
