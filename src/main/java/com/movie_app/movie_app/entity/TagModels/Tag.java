package com.movie_app.movie_app.entity.TagModels;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.movie_app.movie_app.entity.MovieModels.Movie;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    private String name;

    @ManyToMany(mappedBy = "tags") // Tag'in Movie ile olan ilişkisinin ters tarafı
    @JsonIgnore 
    private List<Movie> movies;
}