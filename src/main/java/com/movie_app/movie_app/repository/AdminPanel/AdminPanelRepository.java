package com.movie_app.movie_app.repository.AdminPanel;

import java.util.Optional;
import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movie_app.movie_app.entity.AdminPanelModel.AdminPanelModel;
import com.movie_app.movie_app.entity.MovieModels.Movie;



public interface AdminPanelRepository  extends JpaRepository<AdminPanelModel, Long>{
    @Query("SELECT a FROM AdminPanelModel a WHERE a.userName = :userName")
     Optional<AdminPanelModel> findAdminUserNameByUsername(@Param("userName") String userName);
}
