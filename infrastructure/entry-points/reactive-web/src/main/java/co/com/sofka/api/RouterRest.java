package co.com.sofka.api;

import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.usecase.addpatient.AddPatientUseCase;
import co.com.sofka.usecase.generic.commands.AddPatientCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> addPatient(AddPatientUseCase addPatientUseCase) {
        return route(
                POST("/api/create/patient").and(accept(MediaType.APPLICATION_JSON)), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(addPatientUseCase.apply(
                                request
                                        .bodyToMono(AddPatientCommand.class)
                        ), DomainEvent.class)));

    }
}
