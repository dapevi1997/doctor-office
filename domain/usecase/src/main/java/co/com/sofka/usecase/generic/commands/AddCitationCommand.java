package co.com.sofka.usecase.generic.commands;

import co.com.sofka.usecase.generic.Command;

public class AddCitationCommand extends Command {
    private String weekId;
    private String citaId;
    private String information;
    private String citationState;
    private String patientId;
    public AddCitationCommand() {
    }

    public AddCitationCommand(String weekId, String citaId, String information, String citationState, String patientId) {
        this.citaId = citaId;
        this.information = information;
        this.citationState = citationState;
        this.patientId = patientId;
        this.weekId = weekId;
    }

    public String getCitaId() {
        return citaId;
    }

    public String getInformation() {
        return information;
    }

    public String getCitationState() {
        return citationState;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getWeekId() {
        return weekId;
    }
}
