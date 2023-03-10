package co.com.sofka.model.patient;


import co.com.sofka.model.patient.entities.Review;
import co.com.sofka.model.patient.events.*;
import co.com.sofka.model.patient.generic.AggregateRoot;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.ClinicHistory;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.PersonalData;
import lombok.Getter;
import lombok.Setter;



import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
/*@NoArgsConstructor
@AllArgsConstructor*/
//@Builder(toBuilder = true)
public class Patient  extends AggregateRoot<PatientId> {

    protected Set<Review> reviews;
    protected PersonalData personalData;
    protected ClinicHistory clinicHistory;

    public Patient(PatientId patientId, Set<Review> reviews, PersonalData personalData, ClinicHistory clinicHistory){
        super(patientId);
        subscribe(new PatientChange(this));
        appendChange(new PatientAdded(patientId.value(), personalData.value(), clinicHistory.value())).apply();
    }

    private Patient(PatientId patientId){
        super(patientId);
        subscribe(new PatientChange(this));
    }

    public static Patient from(PatientId patientId, List<DomainEvent> events){
        Patient patient = new Patient(patientId);
        events.forEach(domainEvent -> patient.applyEvent(domainEvent));
        return patient;

    }

    public void addPatient(PatientId patientId, PersonalData personalData, ClinicHistory clinicHistory){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(personalData);
        Objects.requireNonNull(clinicHistory);
        appendChange(new PatientAdded(patientId.value(), personalData.value(), clinicHistory.value()));

    }

    public void updateIdentity(PatientId patientId, PersonalData personalData){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(personalData);
        appendChange(new IdentityUpdated(patientId.value(), personalData.value()));

    }
    public void addReview(){

        appendChange(new ReviewAdded());

    }

    public void getClinicHistory(){

        appendChange(new ClinicHistoryObtained());

    }

    public void deletePatient(){

        appendChange(new PatientDeleted());

    }





    @Override
    public String toString() {
        return "Patient{" +
                "identity=" + personalData +
                ", clinicHistory=" + clinicHistory +
                '}';
    }
}
