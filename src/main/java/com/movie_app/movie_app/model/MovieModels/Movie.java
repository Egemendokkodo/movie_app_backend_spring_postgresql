package com.movie_app.movie_app.model.MovieModels;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.movie_app.movie_app.model.TagModels.Tag;
import com.movie_app.movie_app.model.WatchOptionModels.WatchOption;
import com.movie_app.movie_app.utils.MovieTypeEnum;



@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Enumerated(EnumType.STRING)
    private MovieTypeEnum type;

    private String name;
    private String movieImage;
    private int movieReleaseYear;
    private double movieImdbRate;
    private long movieTotalCommentCount;

    @ManyToMany
    @JoinTable(
        name = "movie_tags", // Tag'lerin tutulduğu tablo
        joinColumns = @JoinColumn(name = "movie_id"), // Movie ile ilişkili olan kolon
        inverseJoinColumns = @JoinColumn(name = "tag_id") // Tag ile ilişkili olan kolon
    )
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(
        name = "movie_watch_options", // İzleme seçeneklerinin tutulduğu tablo
        joinColumns = @JoinColumn(name = "movie_id"), // Movie ile ilişkili olan kolon
        inverseJoinColumns = @JoinColumn(name = "watch_option_id") // WatchOption ile ilişkili olan kolon
    )
    private List<WatchOption> watchOptions;

    @OneToOne(mappedBy = "movie", cascade = CascadeType.ALL)
    private MovieDetails movieDetails;  // Detaylarla ilişki
}
