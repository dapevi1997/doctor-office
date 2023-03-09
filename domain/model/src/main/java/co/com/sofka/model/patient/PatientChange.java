package co.com.sofka.model.patient;


import co.com.sofka.model.patient.events.*;
import co.com.sofka.model.patient.generic.EventChange;
import co.com.sofka.model.patient.values.Identity;

public class PatientChange extends EventChange {

    public PatientChange(Patient patient) {
        apply((PatientAdded event) -> {
            patient.identity = new Identity(event.getIdentity());

        });
        apply((IdentityUpdated event) -> {


        });
        apply((ReviewAdded event) -> {


        });
        apply((ClinicHistoryObtained event) -> {


        });
        apply((PatientDeleted event) -> {


        });


    }
}
