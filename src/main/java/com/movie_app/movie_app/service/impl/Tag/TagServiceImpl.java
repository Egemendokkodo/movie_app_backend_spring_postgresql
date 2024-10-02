package com.movie_app.movie_app.service.impl.Tag;

import java.util.List;

import org.springframework.stereotype.Service;


import com.movie_app.movie_app.model.TagModels.Tag;
import com.movie_app.movie_app.repository.Tag.TagRepository;
import com.movie_app.movie_app.service.Tag.TagService;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        super();
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {

        return tagRepository.findAll();
    }

    @Override
    public Tag getTagByTagId(Integer id) {
        return tagRepository.findById(id).get();
    }

    @Override
    public Boolean addTag(String tagName) {
        if( tagRepository.findByName(tagName).isPresent()){
            throw new RuntimeException("Tag already exists withh tag name :"+tagName);
            
           }else{
            try {


           

                Tag tagInstance = new Tag();
                tagInstance.setName(tagName);
                tagRepository.save(tagInstance);
     
                
                 return true;
             } catch (Exception e) {
                 if( tagRepository.findByName(tagName).isPresent()){
                 throw new RuntimeException("Tag already exists with tag name :"+tagName);
                 
                }
                 return false;
             }
           }
       
    }

}
