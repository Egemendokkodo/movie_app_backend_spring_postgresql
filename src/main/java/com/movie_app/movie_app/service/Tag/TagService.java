package com.movie_app.movie_app.service.Tag;

import java.util.List;


import com.movie_app.movie_app.model.TagModels.Tag;

public interface TagService {
    List<Tag> getAllTags();
    Tag getTagByTagId (Integer id);
    Boolean addTag(String tagName);
    Boolean deleteTagById(Integer id);
}
