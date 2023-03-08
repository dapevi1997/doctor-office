package co.com.sofka.model.patient.entities;


import co.com.sofka.model.patient.generic.Entity;
import co.com.sofka.model.patient.values.Annotation;
import co.com.sofka.model.patient.values.ReviewId;

public class Review extends Entity<ReviewId> {
    private Annotation annotation;

    public Review(ReviewId entityId, Annotation annotation){
        super(entityId);
        this.annotation = annotation;
    }

    public Annotation annotation(){
        return annotation;
    }

}
