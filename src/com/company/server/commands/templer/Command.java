package com.company.server.commands.templer;

import com.company.server.controllers.command_control.ParamBox;
import com.company.server.collection.MusicBandHashSet;
import com.company.server.exceptions.NoAccessException;

public abstract class Command {

    protected MusicBandHashSet receiver; //не должно быть равно null
    protected ParamBox params;
    protected String login;

    public Command(){}

    public Command(MusicBandHashSet receiver, ParamBox params, String login){
        this.receiver = receiver;
        this.params = params;
        this.login = login;
    }

    public Command(MusicBandHashSet receiver){
        this(receiver, null);
    }

    public Command(MusicBandHashSet musicBandHashSet, String login){
        this(musicBandHashSet, null, login);
    }

    public abstract ParamBox execute() throws NoAccessException;

}