package com.movie_app.movie_app.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.movie_app.movie_app.DTO.Movie.DetailedSearchDTO;
import com.movie_app.movie_app.entity.MovieModels.Movie;
import com.movie_app.movie_app.entity.TagModels.Tag;
import com.movie_app.movie_app.repository.Movie.MovieRepository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class MovieSpecification {

    public static Specification<Movie> filter(DetailedSearchDTO dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // watchType boş olamaz
            if (dto.getWatchType() != null) {
                predicates.add(cb.equal(root.get("type"), MovieTypeEnum.valueOf(dto.getWatchType())));
            }

            if (dto.getYearOfMovie() != null) {
                predicates.add(cb.equal(root.get("movieReleaseYear"), dto.getYearOfMovie()));
            }

            if (dto.getImdbScore() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("movieImdbRate"), dto.getImdbScore()));
            }

            if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
                Join<Movie, Tag> tagJoin = root.join("tags", JoinType.INNER);
                predicates.add(tagJoin.get("tagId").in(dto.getTagIds()));
                query.distinct(true); // Aynı film birden fazla tag'e sahipse tekrarı engeller
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Sort getSortByFilter(String filter) {


        /* LOGIC:::
          
        "1" is the highest imdb score

        "2" is the lowest imdb score

        "3" is by year (new to old)

        "4" is by year (old to new)

        "5" is by site score (biggest to smallest)

        "6" is by site score (smallest to largest)

        "7" is by number of views
         
         */

        switch (filter) {
            case "1":
                return Sort.by(Sort.Direction.DESC, "movieImdbRate");
            case "2":
                return Sort.by(Sort.Direction.ASC, "movieImdbRate");
            case "3":
                return Sort.by(Sort.Direction.DESC, "movieReleaseYear");
            case "4":
                return Sort.by(Sort.Direction.ASC, "movieReleaseYear");
            case "5":
                return Sort.by(Sort.Direction.DESC, "movieDetails.websiteRating");
            case "6":
                return Sort.by(Sort.Direction.ASC, "movieDetails.websiteRating");
            case "7":
                return Sort.by(Sort.Direction.DESC, "movieDetails.totalWatched");
            default:
                return Sort.by(Sort.Direction.DESC, "movieId"); // Default sıralama
        }
    }
}
