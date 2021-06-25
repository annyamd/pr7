package com.company.server.commands;

import com.company.server.controllers.command_control.ParamBox;
import com.company.server.collection.MusicBandHashSet;
import com.company.server.commands.templer.Command;
import com.company.server.db.MusicBandsCRUD;
import com.company.server.exceptions.NoAccessException;

public class RemoveByIdCommand extends Command {
    private long id;

    public RemoveByIdCommand(MusicBandHashSet receiverHashSet, ParamBox paramBox, String login){
        super(receiverHashSet, paramBox, login);

        if (paramBox.size() == 1){
            id = (long) paramBox.toUnpack().get().getVal();
        }
    }

    @Override
    public ParamBox execute() throws NoAccessException {

        try {
            receiver.getReadWriteLock().writeLock().lock();
            MusicBandsCRUD.delete(id, login);
            receiver.reload(MusicBandsCRUD.getAll());
        }  finally {
            receiver.getReadWriteLock().writeLock().unlock();
        }

//        receiver.getData().stream()
//                .filter(m -> m.getId() == id)
//                .findAny().ifPresent(musicBand -> receiver.getData().remove(musicBand));
        return null;
    }
}