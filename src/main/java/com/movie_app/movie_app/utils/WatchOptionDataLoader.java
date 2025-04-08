package com.movie_app.movie_app.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.movie_app.movie_app.entity.WatchOptionModels.WatchOption;
import com.movie_app.movie_app.repository.WatchOptions.WatchOptionRepository;

/* @Component
public class WatchOptionDataLoader implements CommandLineRunner {

    private final WatchOptionRepository watchOptionRepository;

    public WatchOptionDataLoader(WatchOptionRepository watchOptionRepository) {
        this.watchOptionRepository = watchOptionRepository;
    }

     @Override
    public void run(String... args) throws Exception {
        // Eğer "Türkçe Dublaj" ve "İngilizce Altyazı" zaten yoksa ekler
        WatchOption turkceDublaj = new WatchOption();
        turkceDublaj.setName("Türkçe Dublaj");
        watchOptionRepository.save(turkceDublaj);

        WatchOption ingilizceAltyazi = new WatchOption();
        ingilizceAltyazi.setName("İngilizce Altyazı");
        watchOptionRepository.save(ingilizceAltyazi);
    } 
}
 */