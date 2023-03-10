package co.com.sofka.usecase.generic.commands;

import co.com.sofka.usecase.generic.Command;

public class UpdatePersonalDataCommand extends Command {
    private String idPatient;
    private String personalData;

    public UpdatePersonalDataCommand() {
    }

    public UpdatePersonalDataCommand(String idPatient, String personalData) {
        this.idPatient = idPatient;
        this.personalData = personalData;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public String getPersonalData() {
        return personalData;
    }
}
