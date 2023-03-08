package co.com.sofka.model.patient.values;


import co.com.sofka.model.patient.generic.ValueObject;

public class Annotation implements ValueObject<String> {
    private String annotation;
    public Annotation(String annotation){
        this.annotation = annotation;
    }

    @Override
    public String value() {
        return annotation;
    }

    public String getAnnotation() {
        return annotation;
    }
}
