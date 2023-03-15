package co.com.sofka.usecase.generic.commands;

import co.com.sofka.usecase.generic.Command;

public class DeletePatientCommand extends Command {
    private String idPatient;
    private String available;

    public DeletePatientCommand() {
    }

    public DeletePatientCommand(String idPatient, String available) {
        this.idPatient = idPatient;
        this.available = available;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public String getAvailable() {
        return available;
    }
}
