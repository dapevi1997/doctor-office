package co.com.sofka.usecase.generic.commands;

import co.com.sofka.usecase.generic.Command;

public class AddPatientCommand extends Command {
    private String patientId;
    private String identity;
    private String clinicHistory;
    public AddPatientCommand() {
    }

    public AddPatientCommand(String patientId, String identity, String clinicHistory) {
        this.patientId = patientId;
        this.identity = identity;
        this.clinicHistory = clinicHistory;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setId(String id) {
        this.patientId = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getClinicHistory() {
        return clinicHistory;
    }

    public void setClinicHistory(String clinicHistory) {
        this.clinicHistory = clinicHistory;
    }
}
