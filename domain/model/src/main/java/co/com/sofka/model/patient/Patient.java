package co.com.sofka.model.patient;
import co.com.sofka.model.patient.entities.Review;
import co.com.sofka.model.patient.events.CitationAdded;
import co.com.sofka.model.patient.events.CitationCanceled;
import co.com.sofka.model.patient.events.PatientAdded;
import co.com.sofka.model.patient.events.WeekStateConsulted;
import co.com.sofka.model.patient.generic.AggregateRoot;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.ClinicHistory;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.Identity;
import co.com.sofka.model.week.values.CitationId;
import co.com.sofka.model.week.values.CitationState;
import co.com.sofka.model.week.values.Infomation;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.List;
import java.util.Objects;

@Getter
@Setter
/*@NoArgsConstructor
@AllArgsConstructor*/
//@Builder(toBuilder = true)
public class Patient  extends AggregateRoot<PatientId> {

    //protected List<Review> reviews;
    protected Identity identity;
    protected ClinicHistory clinicHistory;

    public Patient(PatientId patientId, /*List<Review> reviews,*/ Identity identity, ClinicHistory clinicHistory){
        super(patientId);
        this.identity = identity;
        this.clinicHistory = clinicHistory;
        subscribe(new PatientChange(this));
        appendChange(new PatientAdded(patientId.value(), identity.value(), clinicHistory.value())).apply();
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

    public void addPatient(PatientId patientId, Identity identity, ClinicHistory clinicHistory){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(identity);
        Objects.requireNonNull(clinicHistory);
        appendChange(new PatientAdded(patientId.value(), identity.value(), clinicHistory.value()));

    }

    public void addCitation(CitationId citationId, Infomation infomation, CitationState citationState, PatientId patientId){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(citationId);
        Objects.requireNonNull(infomation);
        Objects.requireNonNull(citationState);
        appendChange(new CitationAdded(citationId.value(), infomation.value(), citationState.value(), patientId.value()));

    }
    public void cancelateCitation(){

        appendChange(new CitationCanceled());

    }
    public void consultWeekState(){

        appendChange(new WeekStateConsulted());

    }

    @Override
    public String toString() {
        return "Patient{" +
                "identity=" + identity +
                ", clinicHistory=" + clinicHistory +
                '}';
    }
}
