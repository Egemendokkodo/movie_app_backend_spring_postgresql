package com.movie_app.movie_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class MovieAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieAppApplication.class, args);
	}   


}
/* @SpringBootApplication
public class MovieAppApplication implements CommandLineRunner {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private MovieRepository movieRepository; // Ensure you have this repository

    @Autowired
    private WatchOptionRepository watchOptionRepository;

    public static void main(String[] args) {
        SpringApplication.run(MovieAppApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Fetch all tags from the database
        List<Tag> tags = tagRepository.findAll();

        // Check if enough tags are available
        if (tags.size() < 6) {
            System.out.println("Not enough tags available to assign to movies.");
            return;
        }

        // Create new movies and associate tags
        Movie movie1 = new Movie();
        movie1.setName("Komedi Macerası");
        movie1.setMovieReleaseYear(2024);
        movie1.setTags(List.of(tags.get(0), tags.get(1))); // Using tags with IDs 1 and 2
        movie1.setType(MovieTypeEnum.MOVIE);
        movie1.setMovieImdbRate(9.2);
        movie1.setMovieTotalCommentCount(277);

        Movie movie2 = new Movie();
        movie2.setName("Gerilim Dolu Gece");
        movie2.setMovieReleaseYear(2024);
        movie2.setTags(List.of(tags.get(2))); // Using tags with IDs 3
        movie2.setType(MovieTypeEnum.MOVIE);
        movie2.setMovieImdbRate(6.5);
        movie2.setMovieTotalCommentCount(522342489);

        Movie movie3 = new Movie();
        movie3.setName("Aksiyon Zamanı");
        movie3.setMovieReleaseYear(2021);
        movie3.setTags(List.of(tags.get(4), tags.get(5))); // Using tags with IDs 5 and 6
        movie3.setType(MovieTypeEnum.MOVIE);
        movie3.setMovieImdbRate(7.2);
        movie3.setMovieTotalCommentCount(498);

        // Create watch options directly with names
        List<WatchOption> watchOptionsForMovie1 = new ArrayList<>();
        watchOptionsForMovie1.add(createWatchOption("Türkçe Dublaj", movie1));
        watchOptionsForMovie1.add(createWatchOption("İngilizce Altyazı", movie1));
        movie1.setWatchOptions(watchOptionsForMovie1);

        List<WatchOption> watchOptionsForMovie2 = new ArrayList<>();
        watchOptionsForMovie2.add(createWatchOption("Türkçe Dublaj", movie2));
        watchOptionsForMovie2.add(createWatchOption("İngilizce Altyazı", movie2));
        movie2.setWatchOptions(watchOptionsForMovie2);



       
        watchOptionRepository.saveAll(watchOptionsForMovie1);
        watchOptionRepository.saveAll(watchOptionsForMovie2);

 // Save movies and watch options to the database
 movieRepository.saveAll(List.of(movie1, movie2, movie3));
        System.out.println("New movies added successfully.");
    }

    private WatchOption createWatchOption(String name, Movie movie) {
        WatchOption watchOption = new WatchOption();
        watchOption.setName(name); // Directly set the name
        watchOption.setMovie(movie);
        return watchOption;
    }
} */