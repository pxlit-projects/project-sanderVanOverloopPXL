package be.pxl.services;

import be.pxl.services.domain.Post;
import be.pxl.services.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(PostRepository postRepository) {
        return args -> {
            postRepository.save(new Post(1, 1, "First Post", "This is the content of the first post", "admin", LocalDateTime.of(2023, 1, 1, 0, 0), true, false, false, ""));
            postRepository.save(new Post(2, 2, "Second Post", "This is the content of the second post", "user", LocalDateTime.of(2023, 2, 1, 0, 3), false, true, false, ""));
            postRepository.save(new Post(3, 2, "Third Post", "This is the content of the third post", "user", LocalDateTime.of(2023, 3, 1, 0, 35), true, false, true, "Needs more details"));
            postRepository.save(new Post(4, 2, "Fourth Post", "This is the content of the Fourth post", "user", LocalDateTime.of(2023, 3, 1, 1, 10), false, true, true, ""));

        };
    }

}