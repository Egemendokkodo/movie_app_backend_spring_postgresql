package com.movie_app.movie_app.controller.dashboard;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie_app.movie_app.DTO.AdminPanel.LoginAdminDTO;
import com.movie_app.movie_app.message.ReturnMessageFromApi;
import com.movie_app.movie_app.service.AdminPanel.AdminPanelService;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/movie-app")
@CrossOrigin
public class AdminPanelController {

    AdminPanelService adminPanelService;
    public AdminPanelController(AdminPanelService adminPanelService){
        this.adminPanelService=adminPanelService;
    }

    @GetMapping("/admin-panel")
    public String getAdminLoginPage() {
        return "admin_panel"; 
    }

    @PostMapping("/login-admin")
    public ResponseEntity<Map<String, Object>> loginAdmin(@RequestBody LoginAdminDTO loginAdminDTO) {
        try {
            adminPanelService.loginAdmin(loginAdminDTO);
            return ReturnMessageFromApi.returnMessageOnSuccess(
                    true,
                    "Admin successfully logged in.",
                    HttpStatus.OK,
                    true);
        } catch (EntityNotFoundException e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
                    );
        }
    }

    @PostMapping("/register-admin")
    public ResponseEntity<Map<String, Object>> registerAdmin(@RequestBody LoginAdminDTO loginAdminDTO) {
        try {
            adminPanelService.registerAdmin(loginAdminDTO);
            return ReturnMessageFromApi.returnMessageOnSuccess(
                    true,
                    "Admin successfully added.",
                    HttpStatus.OK,
                    true);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
                    );
        }
        
    }
    

}
