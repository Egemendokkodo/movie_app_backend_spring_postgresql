package com.movie_app.movie_app.message;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnMessageFromApi {

    private ReturnMessageFromApi(){}


    public static ResponseEntity<Map<String, Object>> returnMessageOnSuccess(Boolean success,String message,HttpStatus status,Object response){
        Map<String, Object> r = new HashMap<>();
        
        r.put("success", success);       
        r.put("message", message);
        if(response instanceof List<?>){
            List<?> responseList = (List<?>) response;
            r.put("total_count", responseList.size());
        }
        r.put("response", response);
        return new ResponseEntity<>(r, status);
    }

    public static ResponseEntity<Map<String, Object>> returnMessageOnError(Boolean success,String message,HttpStatus status){
        Map<String, Object> r = new HashMap<>();
        r.put("success", success);
        r.put("message", message);
        return new ResponseEntity<>(r, status);
    }
}
