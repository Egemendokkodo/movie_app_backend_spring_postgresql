package com.movie_app.movie_app.repository.WatchOptions;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.movie_app.movie_app.entity.WatchOptionModels.WatchOption;

@Repository
public interface WatchOptionRepository extends JpaRepository<WatchOption, Integer> {
    Optional<WatchOption> findByName(String name);
}
