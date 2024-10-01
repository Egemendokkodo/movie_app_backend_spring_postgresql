package com.movie_app.movie_app.model.WatchOptionModels;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.movie_app.movie_app.model.MovieModels.Movie;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class WatchOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer watchOptionId;

    private String name;

    @ManyToMany(mappedBy = "watchOptions") // WatchOption'un Movie ile olan ilişkisinin ters tarafı
    @JsonIgnore
    private List<Movie> movies;
}
