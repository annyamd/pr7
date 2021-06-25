package com.company.server.commands;

import com.company.server.controllers.command_control.ParamBox;
import com.company.server.commands.templer.Command;
import com.company.server.controllers.command_control.Param;
import com.company.server.controllers.command_control.ParamType;
import com.company.server.collection.MusicBandHashSet;

import java.util.stream.Collectors;

public class ShowCommand extends Command {

    public ShowCommand(MusicBandHashSet receiver) {
        super(receiver);
    }

    @Override
    public ParamBox execute() {
        try {
            receiver.getReadWriteLock().readLock().lock();
            return new ParamBox(1).add(new Param(ParamType.LIST, "List",
                    receiver.getData().stream()
                            .sorted(receiver.getNameComparator())
                            .collect(Collectors.toList()))).toPack();
        } finally {
            receiver.getReadWriteLock().readLock().unlock();
        }
    }
}
