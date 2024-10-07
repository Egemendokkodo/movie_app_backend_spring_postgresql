package com.movie_app.movie_app.service.impl.User;

import org.antlr.v4.runtime.InputMismatchException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.movie_app.movie_app.DTO.User.LoginUserDTO;
import com.movie_app.movie_app.DTO.User.UserDTO;
import com.movie_app.movie_app.model.User.Token;
import com.movie_app.movie_app.model.User.User;
import com.movie_app.movie_app.repository.User.UserRepository;
import com.movie_app.movie_app.service.User.TokenRepository;
import com.movie_app.movie_app.service.User.UserService;
import com.movie_app.movie_app.utils.JWTUtil;
import com.movie_app.movie_app.utils.UserRegistrationValidator;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository) {
        super();
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Boolean addUser(UserDTO userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EntityExistsException("User already exists");
        } else {

            UserRegistrationValidator validator = new UserRegistrationValidator();

            validator.validate(userDto);

            try {
                User user = new User();
                user.setName(userDto.getName());
                user.setPassword(userDto.getPassword());
                user.setEmail(userDto.getEmail());
                user.setSurname(userDto.getSurname());
                userRepository.save(user);

                JWTUtil jwtUtil = new JWTUtil();
                String tokenValue = jwtUtil.generateToken(user.getEmail());

                Token token = new Token();
                token.setToken(tokenValue);
                token.setUser(user);
                tokenRepository.save(token);

                return true;
            } catch (Exception e) {
                throw new RuntimeException(e.toString());
            }
        }

    }

    @Override
    public Boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new EntityNotFoundException("User already exists");
        }

    }

    @Override
    public Boolean updateUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> entityNotFoundException(id));
    }

    private EntityNotFoundException entityNotFoundException(long id) {
        return new EntityNotFoundException("User not found with id: " + id);
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    @Override
    public User loginUser(LoginUserDTO loginUserDTO) {
        final Boolean isUserExists = userRepository.findByEmail(loginUserDTO.getEmail()).isPresent();
        if (Boolean.TRUE.equals(isUserExists)) {
            return userRepository.findByEmail(loginUserDTO.getEmail()).get();
        } else {
            throw new EntityNotFoundException("User does not exists");
        }
    }

}
