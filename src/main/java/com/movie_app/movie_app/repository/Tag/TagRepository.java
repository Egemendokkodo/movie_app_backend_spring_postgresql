package com.movie_app.movie_app.repository.Tag;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.movie_app.movie_app.model.TagModels.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name); // Tag adını bulmak için metod
}