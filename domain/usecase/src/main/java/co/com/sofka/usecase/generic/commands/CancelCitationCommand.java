package co.com.sofka.usecase.generic.commands;

import co.com.sofka.usecase.generic.Command;

public class CancelCitationCommand extends Command {
    private String weekId;
    private String citaId;
    private String citationState;

    public CancelCitationCommand() {
    }

    public CancelCitationCommand(String weekId, String citaId, String citationState) {
        this.weekId = weekId;
        this.citaId = citaId;
        this.citationState = citationState;
    }

    public String getWeekId() {
        return weekId;
    }

    public String getCitaId() {
        return citaId;
    }

    public String getCitationState() {
        return citationState;
    }
}
