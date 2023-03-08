package co.com.sofka.usecase.generic.commands;

import co.com.sofka.usecase.generic.Command;

public class AddPatientCommand extends Command {
    private String id;
    private String identity;
    private String clinicHistory;
    public AddPatientCommand() {
    }

    public AddPatientCommand(String id, String identity, String clinicHistory) {
        this.id = id;
        this.identity = identity;
        this.clinicHistory = clinicHistory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
