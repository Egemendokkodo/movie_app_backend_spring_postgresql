package com.movie_app.movie_app.entity.MovieModels;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_watch_history")
public class UserWatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
    private Long movieId;
    private LocalDateTime watchedAt;
    
    // Yapıcı metotlar
    public UserWatchHistory() {}
    
    public UserWatchHistory(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
        this.watchedAt = LocalDateTime.now();
    }
}