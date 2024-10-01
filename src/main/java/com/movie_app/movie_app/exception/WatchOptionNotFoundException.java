package com.movie_app.movie_app.exception;

public class WatchOptionNotFoundException extends RuntimeException {
    public WatchOptionNotFoundException(String message) {
        super(message);
    }
}