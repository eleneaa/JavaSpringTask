package skillbox.mod1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ContactServiceImpl contactService() {
        return new ContactServiceImpl(); // Предположим, что класс ContactServiceImpl реализован и требуется как бин
    }
}

