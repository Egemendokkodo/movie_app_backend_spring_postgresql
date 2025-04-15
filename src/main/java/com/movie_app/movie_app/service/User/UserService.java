package com.movie_app.movie_app.service.User;

import java.util.List;

import com.movie_app.movie_app.DTO.Movie.MovieResponseDTO;
import com.movie_app.movie_app.DTO.User.LoginUserDTO;
import com.movie_app.movie_app.DTO.User.UpdateUserDTO;
import com.movie_app.movie_app.DTO.User.UserAddWatchListDTO;
import com.movie_app.movie_app.DTO.User.UserDTO;
import com.movie_app.movie_app.entity.User.User;

public interface UserService {
    Boolean addUser(UserDTO userDto);
    User loginUser(LoginUserDTO loginUserDTO);
    Boolean deleteUser(Long id);
    Boolean updateUser(UpdateUserDTO updateUserDTO);
    User getUserById(Long id);
    String addToUserWatchList(UserAddWatchListDTO userAddWatchListDTO );
    List<MovieResponseDTO> getUserWatchList(Long userId) ;
    public void removeWatchedMovie(Long userId, Long movieId);
    boolean isUserWatchedThisMovie(Long userId, Long movieId);
}
