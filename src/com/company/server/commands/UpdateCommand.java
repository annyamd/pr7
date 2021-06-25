package com.company.server.commands;

import com.company.server.controllers.command_control.ParamBox;
import com.company.server.collection.MusicBandHashSet;
import com.company.server.commands.templer.Command;
import com.company.server.db.MusicBandsCRUD;
import com.company.server.exceptions.NoAccessException;
import com.company.server.model.MusicBand;

public class UpdateCommand extends Command {

    private long id;
    private MusicBand elem;

    public UpdateCommand(MusicBandHashSet receiver, ParamBox paramBox, String login) {
        super(receiver, paramBox, login);

        if (paramBox.size() == 2) {
            this.id = (long) paramBox.toUnpack().get().getVal();
            this.elem = (MusicBand) paramBox.get().getVal();
        }
    }

    @Override
    public ParamBox execute() throws NoAccessException {

        try {
            receiver.getReadWriteLock().writeLock().lock();
            MusicBandsCRUD.update(elem, id, login);
            receiver.reload(MusicBandsCRUD.getAll());
            return null;
        } finally {
            receiver.getReadWriteLock().writeLock().unlock();
        }
    }
}
