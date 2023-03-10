package co.com.sofka.model.patient;


import co.com.sofka.model.patient.entities.Review;
import co.com.sofka.model.patient.events.*;
import co.com.sofka.model.patient.generic.EventChange;
import co.com.sofka.model.patient.values.Annotation;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.PersonalData;
import co.com.sofka.model.patient.values.ReviewId;

public class PatientChange extends EventChange {

    public PatientChange(Patient patient) {
        apply((PatientAdded event) -> {
            patient.personalData = new PersonalData(event.getIdentity());

        });
        apply((PersonalDataUpdated event) -> {


        });
        apply((ReviewAdded event) -> {
            Review review = new Review(PatientId.of(event.getPatientId()),ReviewId.of(event.getIdReview()),  new Annotation(event.getAnnotation()));
           // patient.reviews.add(review);


        });
        apply((ClinicHistoryObtained event) -> {


        });
        apply((PatientDeleted event) -> {


        });


    }
}
