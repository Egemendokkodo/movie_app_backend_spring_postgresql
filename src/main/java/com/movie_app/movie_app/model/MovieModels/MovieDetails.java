package com.movie_app.movie_app.model.MovieModels;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class MovieDetails {
    @Id
    private Long movieId;  // Movie tablosundaki movieId'yi doğrudan kullanıyoruz

    @OneToOne
    @MapsId
    @JsonIgnore 
    @JoinColumn(name = "movie_id")
    private Movie movie;  // Movie ile birebir ilişki

    private String description;
    private long movieLengthInMins;
    private String movieReleaseCountry;
    private long totalWatched;
    private double websiteRating;
    private String trailer;
}


