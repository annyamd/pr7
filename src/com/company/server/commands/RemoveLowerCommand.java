package com.company.server.commands;

import com.company.server.commands.templer.Command;
import com.company.server.controllers.command_control.ParamBox;
import com.company.server.collection.MusicBandHashSet;
import com.company.server.db.MusicBandsCRUD;
import com.company.server.exceptions.NoAccessException;
import com.company.server.model.MusicBand;

import java.util.List;
import java.util.stream.Collectors;

public class RemoveLowerCommand extends Command {

    private MusicBand elem;

    public RemoveLowerCommand(MusicBandHashSet receiver, ParamBox params, String login){
        super(receiver, params, login);
        if (params.size() == 1){
            elem = (MusicBand) params.toUnpack().get().getVal();
        }
    }

    @Override
    public ParamBox execute() throws NoAccessException {
        try {
            receiver.getReadWriteLock().writeLock().lock();
            List<MusicBand> musicBands = receiver.getData().stream()
                    .filter(musicBand -> elem.compareTo(musicBand) > 0)
                    .collect(Collectors.toList());
            for (MusicBand musicBand : musicBands) {
                MusicBandsCRUD.delete(musicBand.getId(), login);
            }
            receiver.reload(MusicBandsCRUD.getAll());
            return null;
        } finally {
            receiver.getReadWriteLock().writeLock().unlock();
        }
    }
}
