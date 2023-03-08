package co.com.sofka.model.patient.values;


import co.com.sofka.model.patient.generic.Identity;

public class PatientId extends Identity {
    public PatientId(){

    }
    private PatientId(String uuid){
        super(uuid);
    }

    public static PatientId of(String uuid){
        return new PatientId(uuid);
    }
}
