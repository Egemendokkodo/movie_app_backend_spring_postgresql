package com.movie_app.movie_app.repository.Movie;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie_app.movie_app.model.MovieModels.UserWatchHistory;

@Repository
public interface UserWatchHistoryRepository extends JpaRepository<UserWatchHistory, Long> {
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
}
