package com.movie_app.movie_app.service.impl.User;

import java.util.Optional;

import org.antlr.v4.runtime.InputMismatchException;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.movie_app.movie_app.DTO.User.LoginUserDTO;
import com.movie_app.movie_app.DTO.User.UpdateUserDTO;
import com.movie_app.movie_app.DTO.User.UserDTO;
import com.movie_app.movie_app.model.User.Token;
import com.movie_app.movie_app.model.User.User;
import com.movie_app.movie_app.repository.User.UserRepository;
import com.movie_app.movie_app.security.JWTUtil;
import com.movie_app.movie_app.service.User.TokenRepository;
import com.movie_app.movie_app.service.User.UserService;
import com.movie_app.movie_app.utils.UserRegistrationValidator;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
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
    public Boolean updateUser(UpdateUserDTO updateUserDTO) {
        return userRepository.findByEmail(updateUserDTO.getEmail())
                .map(user -> {
                   
                    updateUserFields(user, updateUserDTO);
                    
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);  
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> entityNotFoundException(id));
    }

  

    @Override
    public User loginUser(LoginUserDTO loginUserDTO) {
        Optional<User> user = userRepository.findByEmail(loginUserDTO.getEmail());
        if (user.isPresent() && passwordEncoder.matches(loginUserDTO.getPassword(), user.get().getPassword())) {

            return userRepository.findByEmail(loginUserDTO.getEmail()).get();
        }

        else {
            throw new EntityNotFoundException("Invalid credentials.");
        }
    }

    private void updateUserFields(User user, UpdateUserDTO dto) {
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getSurname() != null) {
            user.setSurname(dto.getSurname());
        }
    }

    private EntityNotFoundException entityNotFoundException(long id) {
        return new EntityNotFoundException("User not found with id: " + id);
    }

}
