package com.movie_app.movie_app.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.movie_app.movie_app.DTO.User.UserDTO;

public class UserRegistrationValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public void validate(UserDTO userDto) {
        if (Boolean.FALSE.equals(userDto.getPassword().equals(userDto.getRePassword()))) {
            throw new RuntimeException("Password and Re-Password do not match.");
        } else if (!isValidEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        } else if (userDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty.");
        } else if (userDto.getSurname().isEmpty()) {
            throw new IllegalArgumentException("Surname must not be empty.");
        }
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
