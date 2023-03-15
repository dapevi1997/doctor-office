package co.com.sofka.api;

import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.usecase.addcitation.AddCitationUseCase;
import co.com.sofka.usecase.addpatient.AddPatientUseCase;
import co.com.sofka.usecase.addreview.AddReviewUseCase;
import co.com.sofka.usecase.addweek.AddWeekUseCase;
import co.com.sofka.usecase.generic.commands.*;
import co.com.sofka.usecase.getcitations.GetCitationUseCase;
import co.com.sofka.usecase.getclinichistory.GetClinicHistoryUseCase;
import co.com.sofka.usecase.updatepersonaldata.UpdatePersonalDataUseCase;
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

    @Bean
    public RouterFunction<ServerResponse> addReview(AddReviewUseCase addReviewUseCase) {
        return route(
                POST("/api/add/review").and(accept(MediaType.APPLICATION_JSON)), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(addReviewUseCase.apply(
                                request
                                        .bodyToMono(AddReviewCommand.class)
                        ), DomainEvent.class)));

    }

    @Bean
    public RouterFunction<ServerResponse> updatePersonalData(UpdatePersonalDataUseCase updatePersonalDataUseCase) {
        return route(
                POST("/api/update/personalData").and(accept(MediaType.APPLICATION_JSON)), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(updatePersonalDataUseCase.apply(
                                request
                                        .bodyToMono(UpdatePersonalDataCommand.class)
                        ), DomainEvent.class)));

    }

    @Bean
    public RouterFunction<ServerResponse> addWeek(AddWeekUseCase addWeekUseCase) {
        return route(
                POST("/api/add/week").and(accept(MediaType.APPLICATION_JSON)), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(addWeekUseCase.apply(
                                request
                                        .bodyToMono(AddWeekCommand.class)
                        ), DomainEvent.class)));

    }

    @Bean
    public RouterFunction<ServerResponse> addCitation(AddCitationUseCase addCitationUseCase) {
        return route(
                POST("/api/add/citation").and(accept(MediaType.APPLICATION_JSON)), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(addCitationUseCase.apply(
                                request
                                        .bodyToMono(AddCitationCommand.class)
                        ), DomainEvent.class)));

    }

    @Bean
    public RouterFunction<ServerResponse> getCitations(GetCitationUseCase getCitationUseCase) {
        return route(
                GET("/api/get/citation").and(accept(MediaType.APPLICATION_JSON)), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getCitationUseCase.apply(
                                request
                                        .bodyToMono(GetCitationCommand.class)
                        ), DomainEvent.class)));

    }

    @Bean
    public RouterFunction<ServerResponse> getClinicHistory(GetClinicHistoryUseCase getClinicHistoryUsecase) {
        return route(
                GET("/api/get/clinicHistory").and(accept(MediaType.APPLICATION_JSON)), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getClinicHistoryUsecase.apply(
                                request
                                        .bodyToMono(GetClinicHistoryCommand.class)
                        ), DomainEvent.class)));

    }



}
