package com.company.server.commands;

import com.company.server.controllers.command_control.ParamBox;
import com.company.server.commands.templer.Command;
import com.company.server.controllers.command_control.Param;
import com.company.server.controllers.command_control.ParamType;
import com.company.server.collection.MusicBandHashSet;

public class InfoCommand extends Command { // у каждой команды свой ресивер?

    public InfoCommand(MusicBandHashSet receiver) {
        super(receiver);
    }

    @Override
    public ParamBox execute() {

        try {
            receiver.getReadWriteLock().readLock().lock();
            String info = receiver.getInfo();
            return new ParamBox(1).add(new Param(ParamType.STRING, Param.NO_NAME_FIELD, info)).toPack();
        } finally {
            receiver.getReadWriteLock().readLock().unlock();
        }

    }
}
