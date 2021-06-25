package com.company.server.commands;

import com.company.server.controllers.command_control.ParamBox;
import com.company.server.collection.MusicBandHashSet;
import com.company.server.commands.templer.Command;
import com.company.server.db.MusicBandsCRUD;

public class ClearCommand extends Command {


    public ClearCommand(MusicBandHashSet receiver, String login) {
        super(receiver, login);
    }

    @Override
    public ParamBox execute() {
        try {
            receiver.getReadWriteLock().writeLock().lock();
            MusicBandsCRUD.deleteAll(login);
            receiver.reload(MusicBandsCRUD.getAll());
        } finally {
            receiver.getReadWriteLock().writeLock().unlock();
        }
        return null;
    }
}
