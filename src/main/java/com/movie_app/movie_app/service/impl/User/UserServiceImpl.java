package com.movie_app.movie_app.service.impl.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.InputMismatchException;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.movie_app.movie_app.DTO.Movie.MovieDetailsDTO;
import com.movie_app.movie_app.DTO.Movie.MovieResponseDTO;
import com.movie_app.movie_app.DTO.Movie.TagDTO;
import com.movie_app.movie_app.DTO.Movie.WatchOptionDTO;
import com.movie_app.movie_app.DTO.User.LoginUserDTO;
import com.movie_app.movie_app.DTO.User.UpdateUserDTO;
import com.movie_app.movie_app.DTO.User.UserAddWatchListDTO;
import com.movie_app.movie_app.DTO.User.UserDTO;
import com.movie_app.movie_app.entity.MovieModels.Movie;
import com.movie_app.movie_app.entity.User.Token;
import com.movie_app.movie_app.entity.User.User;
import com.movie_app.movie_app.entity.User.UserWatchListEntity;
import com.movie_app.movie_app.repository.Movie.MovieRepository;
import com.movie_app.movie_app.repository.User.UserRepository;
import com.movie_app.movie_app.repository.User.UserWatchListRepository;
import com.movie_app.movie_app.security.JWTUtil;
import com.movie_app.movie_app.service.User.TokenRepository;
import com.movie_app.movie_app.service.User.UserService;
import com.movie_app.movie_app.utils.UserRegistrationValidator;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private UserWatchListRepository userWatchListRepository;
    private MovieRepository movieRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository,
            UserWatchListRepository userWatchListRepository, MovieRepository movieRepository) {
        super();
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.userWatchListRepository = userWatchListRepository;
        this.movieRepository = movieRepository;
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
                user.setUsername(userDto.getUserName());
                userRepository.save(user);

                JWTUtil jwtUtil = new JWTUtil();
                String tokenValue = jwtUtil.generateToken(user.getEmail());
                System.out.println("kaydedilen username :: " + userDto.getUserName());

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
            throw new EntityNotFoundException("User does not exists");
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

    @Override
    public String addToUserWatchList(UserAddWatchListDTO dto) {
        Movie movie = dto.getMovie();

        // Filmi veritabanında kontrol et (zaten kayıtlı mı)
        Optional<Movie> existingMovie = movieRepository.findById(movie.getMovieId());
        if (existingMovie.isEmpty()) {
            throw new RuntimeException("Movie not found");
        }

        // Kullanıcının watchlist'ini kontrol et
        UserWatchListEntity watchList = userWatchListRepository.findByUserId(dto.getUserId())
                .orElseGet(() -> {
                    UserWatchListEntity newWatchList = new UserWatchListEntity();
                    newWatchList.setUserId(dto.getUserId());
                    return newWatchList;
                });

        // Eğer film zaten ekliyse tekrar ekleme
        if (watchList.getMovieList().contains(existingMovie.get())) {
            return "Movie already exists in watchlist";
        }

        watchList.getMovieList().add(existingMovie.get());
        userWatchListRepository.save(watchList);

        return "Movie added to watchlist";
    }

    @Override
    public List<MovieResponseDTO> getUserWatchList(Long userId) {
        Optional<UserWatchListEntity> optionalWatchList = userWatchListRepository.findByUserId(userId);
        if (optionalWatchList.isEmpty())
            return new ArrayList<>();

        List<Movie> movieList = optionalWatchList.get().getMovieList();

        return movieList.stream().map(movie -> MovieResponseDTO.builder()
                .movieId(movie.getMovieId())
                .type(movie.getType().name())
                .name(movie.getName())
                .movieImage(movie.getMovieImage())
                .movieReleaseYear(movie.getMovieReleaseYear())
                .movieImdbRate(movie.getMovieImdbRate())
                .movieTotalCommentCount(movie.getMovieTotalCommentCount())
                .tags(Optional.ofNullable(movie.getTags()).orElse(new ArrayList<>())
                        .stream()
                        .map(tag -> TagDTO.builder()
                                .tagId(tag.getTagId())
                                .name(tag.getName())
                                .build())
                        .collect(Collectors.toList()))
                .watchOptions(Optional.ofNullable(movie.getWatchOptions()).orElse(new ArrayList<>())
                        .stream()
                        .map(option -> WatchOptionDTO.builder()
                                .watchOptionId(option.getWatchOptionId())
                                .name(option.getName())
                                .build())
                        .collect(Collectors.toList()))
                .movieDetails(movie.getMovieDetails() != null ? MovieDetailsDTO.builder()
                        .movieId(movie.getMovieDetails().getMovieId())
                        .description(movie.getMovieDetails().getDescription())
                        .movieLengthInMins((int) movie.getMovieDetails().getMovieLengthInMins()) // DÜZELTME BURADA
                        .movieReleaseCountry(movie.getMovieDetails().getMovieReleaseCountry())
                        .totalWatched(movie.getMovieDetails().getTotalWatched())
                        .websiteRating(movie.getMovieDetails().getWebsiteRating())
                        .trailer(movie.getMovieDetails().getTrailer())
                        .build() : null)
                .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeWatchedMovie(Long userId, Long movieId) {
        Optional<UserWatchListEntity> optional = userWatchListRepository.findByUserId(userId);
        if (optional.isEmpty())
            throw new RuntimeException("User not found");

        UserWatchListEntity entity = optional.get();
        List<Movie> movies = entity.getMovieList();

        movies.removeIf(movie -> movie.getMovieId().equals(movieId));
        userWatchListRepository.save(entity); // değişikliği kaydet
    }

    @Override
    public boolean isUserWatchedThisMovie(Long userId, Long movieId) {
        Optional<UserWatchListEntity> optional = userWatchListRepository.findByUserId(userId);
        if (optional.isEmpty())
            return false;

        List<Movie> watchedMovies = optional.get().getMovieList();
        return watchedMovies.stream()
                .anyMatch(movie -> movie.getMovieId().equals(movieId));
    }
}
