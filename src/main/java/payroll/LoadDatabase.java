package payroll;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repo) {
        return args -> {
            log.info("Preloading "  + repo.save(new User("Father", "Bear", "vlad.seden@gmail.com", "admin", "")));
        };
    }
}
