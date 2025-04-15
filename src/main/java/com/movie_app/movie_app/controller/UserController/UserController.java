package com.movie_app.movie_app.controller.UserController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import com.movie_app.movie_app.DTO.Movie.MovieResponseDTO;
import com.movie_app.movie_app.DTO.User.LoginUserDTO;
import com.movie_app.movie_app.DTO.User.UserAddWatchListDTO;
import com.movie_app.movie_app.DTO.User.UserDTO;
import com.movie_app.movie_app.entity.User.User;
import com.movie_app.movie_app.message.ReturnMessageFromApi;
import com.movie_app.movie_app.service.User.UserService;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody UserDTO userDTO) {
        try {
            userService.addUser(userDTO);
            return ReturnMessageFromApi.returnMessageOnSuccess(
                    true,
                    "User successfully added.",
                    HttpStatus.OK,
                    true);
        } catch (EntityExistsException e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    e.getMessage(),
                    HttpStatus.CONFLICT); // 409 Conflict
        } catch (IllegalArgumentException e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    "An unexpected error occurred.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody LoginUserDTO userLoginDTO) {
        try {
            User user = userService.loginUser(userLoginDTO);
            if (user != null) {

                return ReturnMessageFromApi.returnMessageOnSuccess(true, "Logged in successfully.", HttpStatus.OK,
                        user);
            } else {
                return ReturnMessageFromApi.returnMessageOnError(false,
                        "Something went wrong , please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (EntityNotFoundException e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    "An unexpected error occurred.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-user-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {

                return ReturnMessageFromApi.returnMessageOnSuccess(true, "User get successfully.", HttpStatus.OK, user);
            } else {
                return ReturnMessageFromApi.returnMessageOnError(false,
                        "Something went wrong , please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (EntityNotFoundException e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    "An unexpected error occurred.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete-user/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {

        try {
            userService.deleteUser(id);
            return ReturnMessageFromApi.returnMessageOnSuccess(
                    true,
                    "User successfully deleted with id: " + id,
                    HttpStatus.OK,
                    true);
        }

        catch (EntityNotFoundException e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-to-watchlist")
    public ResponseEntity<Map<String, Object>> addToWatchList(@RequestBody UserAddWatchListDTO userAddWatchListDTO) {

        try {
            userService.addToUserWatchList(userAddWatchListDTO);
            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Successfully added to watched list",
                    HttpStatus.OK, userAddWatchListDTO);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(false,
                    "Something went wrong , please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/watchlist/{userId}")
    public ResponseEntity<?> getUserWatchList(@PathVariable Long userId) {
        try {
            List<MovieResponseDTO> movies = userService.getUserWatchList(userId);
            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Watchlist fetched successfully", HttpStatus.OK,
                    movies);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(false, "Failed to fetch watchlist",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove-from-watched")
    public ResponseEntity<Map<String, Object>> removeFromWatched(
            @RequestParam Long userId,
            @RequestParam Long movieId) {
        try {
            userService.removeWatchedMovie(userId, movieId);
            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Movie removed from watched list", HttpStatus.OK,
                    null);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(false, "Something went wrong",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/is-user-watched")
public ResponseEntity<Map<String, Object>> isUserWatchedThisMovie(
        @RequestParam Long userId,
        @RequestParam Long movieId) {
    boolean isWatched = userService.isUserWatchedThisMovie(userId, movieId);
    return ReturnMessageFromApi.returnMessageOnSuccess(true,
            isWatched ? "User has watched this movie" : "User has not watched this movie",
            HttpStatus.OK, isWatched);
}

}
