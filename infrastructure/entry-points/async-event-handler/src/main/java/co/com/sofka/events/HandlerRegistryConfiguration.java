package co.com.sofka.events;

import co.com.sofka.events.handlers.CommandsHandler;
import co.com.sofka.events.handlers.EventsHandler;
import co.com.sofka.events.handlers.QueriesHandler;
//import org.reactivecommons.async.api.HandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerRegistryConfiguration {

    // see more at: https://reactivecommons.org/reactive-commons-java/#_handlerregistry_2
/*    @Bean
    public HandlerRegistry handlerRegistry(CommandsHandler commands, EventsHandler events, QueriesHandler queries) {
        return HandlerRegistry.register()
                .listenNotificationEvent("some.broadcast.event.name", events::handleEventA, Object.class*//*change for proper model*//*)
                .listenEvent("some.event.name", events::handleEventA, Object.class*//*change for proper model*//*)
                .handleCommand("some.command.name", commands::handleCommandA, Object.class*//*change for proper model*//*)
                .serveQuery("some.query.name", queries::handleQueryA, Object.class*//*change for proper model*//*);
    }*/
}
