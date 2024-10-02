package com.movie_app.movie_app.repository.Movie;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movie_app.movie_app.model.MovieModels.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Tag ID'lerine göre filmleri döndüren sorgu
    @Query("SELECT m FROM Movie m JOIN m.tags t WHERE t.tagId IN :tagIds")
    List<Movie> findMoviesByTagIds(@Param("tagIds") List<Integer> tagIds);
    
}