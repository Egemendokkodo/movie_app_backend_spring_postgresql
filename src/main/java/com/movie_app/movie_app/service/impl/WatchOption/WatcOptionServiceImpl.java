package com.movie_app.movie_app.service.impl.WatchOption;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

import com.movie_app.movie_app.model.WatchOptionModels.WatchOption;
import com.movie_app.movie_app.repository.Tag.TagRepository;
import com.movie_app.movie_app.repository.WatchOptions.WatchOptionRepository;
import com.movie_app.movie_app.service.WatchOption.WatchOptionService;

@Service
public class WatcOptionServiceImpl implements WatchOptionService {
 private WatchOptionRepository watchOptionRepository;

    
    public WatcOptionServiceImpl(WatchOptionRepository watchOptionRepository){
        super();
        this.watchOptionRepository=watchOptionRepository;
    }


    @Override
    public List<WatchOption> getAllWatchOptions() {
        return watchOptionRepository.findAll();
    }


    
    
}
