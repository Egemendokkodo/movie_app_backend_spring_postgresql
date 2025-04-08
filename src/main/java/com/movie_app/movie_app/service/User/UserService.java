package com.movie_app.movie_app.service.User;

import com.movie_app.movie_app.DTO.User.LoginUserDTO;
import com.movie_app.movie_app.DTO.User.UpdateUserDTO;
import com.movie_app.movie_app.DTO.User.UserDTO;
import com.movie_app.movie_app.entity.User.User;

public interface UserService {
    Boolean addUser(UserDTO userDto);
    User loginUser(LoginUserDTO loginUserDTO);
    Boolean deleteUser(Long id);
    Boolean updateUser(UpdateUserDTO updateUserDTO);
    User getUserById(Long id);
}
