package co.com.sofka.model.patient.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class PatientAdded extends DomainEvent {
    private String id;
    private String identity;
    private String clinicHistory;

    private String reviewId;
    private String annotation;

    public PatientAdded() {
        super("perez.daniel.patientadded");
    }
    public PatientAdded(String id, String reviewId, String annotation,String identity, String clinicHistory){
        super("perez.daniel.patientadded");
        this.id = id;
        this.identity = identity;
        this.clinicHistory = clinicHistory;
        this.reviewId = reviewId;
        this.annotation = annotation;

    }

    public String getId() {
        return id;
    }

    public String getIdentity() {
        return identity;
    }

    public String getClinicHistory() {
        return clinicHistory;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getAnnotation() {
        return annotation;
    }
}
