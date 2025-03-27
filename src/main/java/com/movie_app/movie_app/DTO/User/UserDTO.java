package com.movie_app.movie_app.DTO.User;

import lombok.Data;

@Data
public class UserDTO {
    String email;
    String password;
    String rePassword;
    String name;
    String surname;
    String userName;
}
