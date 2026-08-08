package url_shortener.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MapConfig {

    @Bean
    public HashMap<String,String> getMap(){
        return new HashMap<>();
    }
}
