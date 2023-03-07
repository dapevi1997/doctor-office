package co.com.sofka.config;

import co.com.sofka.serializer.JSONMapper;
import co.com.sofka.serializer.JSONMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenericConfig {
    @Bean
    public JSONMapper jsonMapper(){
        return new JSONMapperImpl();
    }
}
