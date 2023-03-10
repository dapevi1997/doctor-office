package co.com.sofka.model.patient;


import co.com.sofka.model.patient.events.*;
import co.com.sofka.model.patient.generic.EventChange;
import co.com.sofka.model.patient.values.PersonalData;

public class PatientChange extends EventChange {

    public PatientChange(Patient patient) {
        apply((PatientAdded event) -> {
            patient.personalData = new PersonalData(event.getIdentity());

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
