package com.movie_app.movie_app.service.User;

import com.movie_app.movie_app.DTO.User.LoginUserDTO;
import com.movie_app.movie_app.DTO.User.UserDTO;
import com.movie_app.movie_app.model.User.User;

public interface UserService {
    Boolean addUser(UserDTO userDto);
    User loginUser(LoginUserDTO loginUserDTO);
    Boolean deleteUser(Long id);
    Boolean updateUser();
    User getUserById(Long id);
}
