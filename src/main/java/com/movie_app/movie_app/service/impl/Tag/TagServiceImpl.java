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
                 
                 return false;
             }
           }
       
    }
    @Override
    public Boolean deleteTagById(Integer id){
        if( Boolean.FALSE.equals(tagRepository.findById(id).isPresent())){
            throw new RuntimeException("Tag does not exists with tag id :"+Integer.toString(id));
            
           }else{
            try {

                tagRepository.deleteById(id);
           
     
                
                 return true;
             } catch (Exception e) {
                 
                 return false;
             }
           }


    }

}
