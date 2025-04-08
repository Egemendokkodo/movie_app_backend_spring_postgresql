package com.movie_app.movie_app.controller.TagControllers;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.movie_app.movie_app.DTO.Movie.TagAddDTO;
import com.movie_app.movie_app.entity.TagModels.Tag;
import com.movie_app.movie_app.message.ReturnMessageFromApi;
import com.movie_app.movie_app.service.Tag.TagService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/tag")
@CrossOrigin
public class TagController {
    private TagService tagService;

    public TagController(TagService tagService) {
        super();
        this.tagService = tagService;
    }

    @GetMapping(path = "/get-all-tags")
    public ResponseEntity<Map<String, Object>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ReturnMessageFromApi.returnMessageOnSuccess(true, "Tags fetched successfully", HttpStatus.OK, tags);
    }

    @GetMapping(path = "/get-tag-by-tag-id/{tagId}")
    public ResponseEntity<Map<String, Object>> getTagByTagId(@PathVariable Integer tagId) {
        try {
            Tag tag = tagService.getTagByTagId(tagId);
            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Tags fetched successfully", HttpStatus.OK, tag);
        } catch (NoSuchElementException e) {

            return ReturnMessageFromApi.returnMessageOnError(false, "No tag found with tag id : " + tagId,
                    HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/add-tag")
    public ResponseEntity<Map<String, Object>> addTag(@RequestBody TagAddDTO tag) {
        
        Boolean success=tagService.addTag(tag.getName());
        if(Boolean.TRUE.equals(success)){
            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Tag added successfully : "+tag.toString(), HttpStatus.OK, tag);
        }else{
            return ReturnMessageFromApi.returnMessageOnError(false, "Cannot add tag : " + tag.toString(),
            HttpStatus.BAD_REQUEST);
        }
        
       
    }
    @PostMapping("/delete-tag-by-id")
    public ResponseEntity<Map<String, Object>> deleteTag(@RequestBody Integer tagId) {

        Boolean success=tagService.deleteTagById(tagId);
        if(Boolean.TRUE.equals(success)){
            return ReturnMessageFromApi.returnMessageOnSuccess(true, "Tag deleted successfully", HttpStatus.OK, Integer.toString(tagId));
        }else{
            return ReturnMessageFromApi.returnMessageOnError(false, "Cannot delete tag : " ,
            HttpStatus.BAD_REQUEST);
        }
       
    }
     

    
}
