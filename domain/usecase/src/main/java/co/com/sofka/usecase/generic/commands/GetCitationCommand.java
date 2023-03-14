package co.com.sofka.usecase.generic.commands;

import co.com.sofka.usecase.generic.Command;

public class GetCitationCommand extends Command {
    String idWeek;

    public GetCitationCommand() {
    }

    public GetCitationCommand(String idWeek) {
        this.idWeek = idWeek;
    }

    public String getIdWeek() {
        return idWeek;
    }
}
