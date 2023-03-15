package co.com.sofka.model.patient;


import co.com.sofka.model.patient.entities.Review;
import co.com.sofka.model.patient.events.*;
import co.com.sofka.model.patient.generic.AggregateRoot;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.*;
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

    protected Available available;

    public Patient(PatientId patientId, ReviewId reviewId, Annotation annotation, PersonalData personalData, ClinicHistory clinicHistory, Available available){
        super(patientId);
        subscribe(new PatientChange(this));
        appendChange(new PatientAdded(patientId.value(), reviewId.value(), annotation.value(), personalData.value(), clinicHistory.value(), available.value().toString())).apply();
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

    public void addPatient(PatientId patientId, ReviewId reviewId, Annotation annotation, PersonalData personalData, ClinicHistory clinicHistory, Available available){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(personalData);
        Objects.requireNonNull(clinicHistory);
        appendChange(new PatientAdded(patientId.value(), reviewId.value(), annotation.value(), personalData.value(), clinicHistory.value(), available.value().toString()));

    }

    public void updatePersonalData(PatientId patientId, PersonalData personalData){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(personalData);
        subscribe(new PatientChange(this));
        appendChange(new PersonalDataUpdated(patientId.value(), personalData.value()));

    }
    public void updateAvailable(PatientId patientId, Available available){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(available);
        subscribe(new PatientChange(this));
        appendChange(new PatientDeleted(patientId.value(), available.value().toString()));

    }
    public void addReview(PatientId patientId, ReviewId reviewId, Annotation annotation){
        subscribe(new PatientChange(this));
        appendChange(new ReviewAdded(patientId.value(), reviewId.value(), annotation.value()));

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
                "reviews=" + reviews +
                ", personalData=" + personalData +
                ", clinicHistory=" + clinicHistory +
                '}';
    }
}
