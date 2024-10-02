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

import com.movie_app.movie_app.message.ReturnMessageFromApi;
import com.movie_app.movie_app.model.TagModels.Tag;
import com.movie_app.movie_app.service.Tag.TagService;

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

    
}
