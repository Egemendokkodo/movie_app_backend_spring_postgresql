package com.movie_app.movie_app.service.AdminPanel;

import com.movie_app.movie_app.DTO.AdminPanel.LoginAdminDTO;

public interface AdminPanelService {
    public Boolean loginAdmin(LoginAdminDTO loginAdminDTO);
    public Boolean registerAdmin(LoginAdminDTO loginAdminDTO);
}
