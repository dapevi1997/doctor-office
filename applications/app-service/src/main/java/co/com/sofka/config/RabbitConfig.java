package co.com.sofka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "core-posts-events";
    public static final String EVENTS_QUEUE = "events.queue";

    public static final String GENERAL_QUEUE = "general.queue";
    public static final String ROUTING_KEY = "events.routing.key";
    public static final String ROUTING_KEY_GENERAL = "events.#";

    @Bean
    public RabbitAdmin
}
