package com.movie_app.movie_app.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.movie_app.movie_app.entity.MovieModels.Movie;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String content;
  private boolean containsSpoiler;
  private int likeCount = 0;
  private int dislikeCount = 0;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now(); 

  @ManyToOne
  @JoinColumn(name = "movie_id")
  @JsonIgnore
  private Movie movie;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  @JsonIgnore
  private Comment parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Comment> replies = new ArrayList<>();

}