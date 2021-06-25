package com.company.server.commands;

import com.company.server.controllers.command_control.ParamBox;
import com.company.server.collection.MusicBandHashSet;
import com.company.server.commands.templer.Command;
import com.company.server.db.MusicBandsCRUD;
import com.company.server.exceptions.NoAccessException;
import com.company.server.model.MusicBand;

public class AddCommand extends Command {

    private MusicBand elem;

    public AddCommand(MusicBandHashSet receiver, ParamBox params, String login){
        super(receiver, params, login);
        if (params.size() == 1){
            this.elem = (MusicBand) (params.toUnpack().get().getVal());
        }
    }

    @Override
    public ParamBox execute() throws NoAccessException {
        try {
            receiver.getReadWriteLock().writeLock().lock();
            MusicBandsCRUD.add(elem, login);
            receiver.add(elem);
        } finally {
            receiver.getReadWriteLock().writeLock().unlock();
        }

        return null;
    }

}