package com.movie_app.movie_app.model.AdminPanelModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "admin_info")
public class AdminPanelModel {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    private String userName;
    private String password;
}
