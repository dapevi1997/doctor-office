package co.com.sofka.model.patient.values;

import co.com.sofka.model.patient.generic.ValueObject;

public class ClinicHistory implements ValueObject<String> {
    private String clinicHistory;

    public ClinicHistory(String clinicHistory) {
        this.clinicHistory = clinicHistory;
    }

    @Override
    public String value() {
        return clinicHistory;
    }

    public String getClinicHistory() {
        return clinicHistory;
    }

    @Override
    public String toString() {
        return "ClinicHistory{" +
                "clinicHistory='" + clinicHistory + '\'' +
                '}';
    }
}
