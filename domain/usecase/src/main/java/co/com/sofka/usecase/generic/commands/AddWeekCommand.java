package co.com.sofka.usecase.generic.commands;

import co.com.sofka.model.week.values.Availability;
import co.com.sofka.usecase.generic.Command;

public class AddWeekCommand extends Command {

    private String weekId;
    private String citaId;
    private String information;
    private String citationState;
    private String patientId;

    private String availability;
    private String state;
    private String date;
    public AddWeekCommand() {
    }

    public AddWeekCommand(String weekId, String citaId, String information, String citationState, String patientId,String availability ,String state, String date) {
        this.weekId = weekId;
        this.citaId = citaId;
        this.information = information;
        this.citationState = citationState;
        this.patientId = patientId;
        this.state = state;
        this.date = date;
        this.availability = availability;
    }

    public String getWeekId() {
        return weekId;
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

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }

    public String getAvailability() {
        return availability;
    }
}
