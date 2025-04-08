package com.movie_app.movie_app.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movie_app.movie_app.entity.TagModels.Tag;
import com.movie_app.movie_app.repository.Tag.TagRepository;

import jakarta.annotation.PostConstruct;

@Component
public class TagUtils {


    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }


    
/*       @PostConstruct
    public void init() {
        // Tüm tag'leri sil
        tagRepository.deleteAll();
        if(tagRepository.count()<=0){
 // Yeni tag'leri oluştur
 List<String> tagNames = Arrays.asList(
    "Aile",
    "Aksiyon",
    "Animasyon",
    "Belgesel",
    "Bilim Kurgu",
    "Biyografi",
    "Dram",
    "Fantastik",
    "Film-Noir",
    "Game-Show",
    "Gerilim",
    "Gizem",
    "Komedi",
    "Korku",
    "Macera",
    "Müzik",
    "Polisiye",
    "Reality",
    "Reality-TV",
    "Romantik",
    "Savaş",
    "Science Fiction",
    "Short",
    "Spor",
    "Suç",
    "Tarih",
    "TV Movie",
    "Western"
);

// Tag'leri kaydet
for (String tagName : tagNames) {
    Tag tag = new Tag();
    tag.setName(tagName);
    tagRepository.save(tag);
}
        }

       
    }   */
}
