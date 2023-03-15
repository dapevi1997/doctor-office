package co.com.sofka.usecase.generic.commands;

import co.com.sofka.usecase.generic.Command;

public class GetClinicHistoryCommand extends Command {
    private String idPatient;

    public GetClinicHistoryCommand() {
    }
    public GetClinicHistoryCommand(String idPatient) {
        this.idPatient = idPatient;
    }

    public String getIdPatient() {
        return idPatient;
    }
}
