package co.com.sofka.model.patient.entities;


import co.com.sofka.model.patient.generic.Entity;
import co.com.sofka.model.patient.values.Annotation;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.ReviewId;

public class Review extends Entity<ReviewId> {

    private PatientId patientId;
    private Annotation annotation;


    public Review(ReviewId reviewId, PatientId patientId,Annotation annotation) {

        super(reviewId);


        this.annotation = annotation;
    }


    public Annotation getAnnotation() {
        return annotation;
    }

    public PatientId getPatientId() {
        return patientId;
    }
}
