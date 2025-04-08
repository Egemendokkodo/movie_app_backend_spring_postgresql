package com.movie_app.movie_app.controller.WatchOption;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import com.movie_app.movie_app.DTO.User.UserDTO;
import com.movie_app.movie_app.entity.WatchOptionModels.WatchOption;
import com.movie_app.movie_app.message.ReturnMessageFromApi;
import com.movie_app.movie_app.service.WatchOption.WatchOptionService;

import jakarta.persistence.EntityExistsException;

@RestController
@RequestMapping("/api/watch-option")
public class WatchOptionController {
    private WatchOptionService watchOptionService;
    public WatchOptionController(WatchOptionService watchOptionService){
        this.watchOptionService=watchOptionService;
    }

    @GetMapping("/get-all-watch-options")
    public ResponseEntity<Map<String, Object>> getAllWatchOptions() {
        try {
           List<WatchOption> list=watchOptionService.getAllWatchOptions();
            return ReturnMessageFromApi.returnMessageOnSuccess(
                    true,
                    "Sucessfully fetched "+list.size()+" watch options.",
                    HttpStatus.OK,
                    list);
        } catch (Exception e) {
            return ReturnMessageFromApi.returnMessageOnError(
                    false,
                    "An unexpected error occurred.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
