package com.movie_app.movie_app.service.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie_app.movie_app.model.User.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
}

