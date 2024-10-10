package com.movie_app.movie_app.service.impl.AdminPanel;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.movie_app.movie_app.DTO.AdminPanel.LoginAdminDTO;
import com.movie_app.movie_app.model.AdminPanelModel.AdminPanelModel;
import com.movie_app.movie_app.repository.AdminPanel.AdminPanelRepository;
import com.movie_app.movie_app.service.AdminPanel.AdminPanelService;
import com.movie_app.movie_app.utils.UserRegistrationValidator;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminPanelServiceImpl implements AdminPanelService{
    AdminPanelRepository adminPanelRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminPanelServiceImpl(AdminPanelRepository adminPanelRepository){
        this.adminPanelRepository=adminPanelRepository;
    }



    @Override
    public Boolean loginAdmin(LoginAdminDTO loginAdminDTO) {
        
        Optional<AdminPanelModel> admin=adminPanelRepository.findAdminUserNameByUsername(loginAdminDTO.getUserName());

        
        if(admin.isPresent() && passwordEncoder.matches(loginAdminDTO.getPassword(), admin.get().getPassword())){
            return true;
        }
        else{
            throw new EntityNotFoundException("Invalid Credentials.");
        }
       

    }

    @Override
    public Boolean registerAdmin(LoginAdminDTO loginAdminDTO) {
        Optional<AdminPanelModel> admin=adminPanelRepository.findAdminUserNameByUsername(loginAdminDTO.getUserName());

        if(admin.isPresent()){
            throw new EntityExistsException("User Already Exists with id : "+admin.get().getAdminId());
        }else{
            try {
                AdminPanelModel adminPanelModel=new AdminPanelModel();
                adminPanelModel.setPassword(passwordEncoder.encode(loginAdminDTO.getPassword()));
                adminPanelModel.setUserName(loginAdminDTO.getUserName());
                adminPanelRepository.save(adminPanelModel);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e.toString());
            }
        }
    }
    
}
