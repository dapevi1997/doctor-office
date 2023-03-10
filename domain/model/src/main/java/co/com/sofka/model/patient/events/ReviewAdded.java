package co.com.sofka.model.patient.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class ReviewAdded extends DomainEvent {
    private String idReview;
    private String annotation;

    private String patientId;

    public ReviewAdded() {
        super("perez.daniel.reviewadded");
    }
    public ReviewAdded(String patientId,String idReview, String annotation ){
        super("perez.daniel.reviewadded");
        this.idReview = idReview;
        this.annotation = annotation;
        this.patientId = patientId;

    }

    public String getIdReview() {
        return idReview;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getPatientId() {
        return patientId;
    }
}
