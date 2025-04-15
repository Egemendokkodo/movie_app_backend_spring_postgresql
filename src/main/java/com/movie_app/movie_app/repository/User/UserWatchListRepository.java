package com.movie_app.movie_app.repository.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie_app.movie_app.entity.User.UserWatchListEntity;

@Repository
public interface UserWatchListRepository extends JpaRepository<UserWatchListEntity, Long> {
    Optional<UserWatchListEntity> findByUserId(Long userId);
}
