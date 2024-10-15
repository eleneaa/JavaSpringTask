package skillbox.mod1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ContactService contactService() {
        return new ContactServiceImpl();
    }

    @Bean
    public ContactFileHandler contactFileHandler() {
        return new ContactFileHandler();
    }

    @Bean
    public ContactFileHandler anotherContactFileHandler() {
        return new ContactFileHandler();
    }

}

