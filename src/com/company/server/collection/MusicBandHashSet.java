package com.company.server.collection;

import com.company.server.model.MusicBand;
import com.company.server.commands.templer.Command;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Expanded {@code HashSet}, which stores {@code MusicBand} Objects
 * Receiver for Commands
 * @see MusicBand
 * @see Command
 */

public class MusicBandHashSet /*implements Set<MusicBandHashSet>*/ {

    /**
     * The date of the initialization of {@code MusicBandHashSet}
     */

    private final Date initTime;
    private final HashSet<MusicBand> data;
    private final Comparator<MusicBand> nameComparator;
    private ReadWriteLock readWriteLock;

    public MusicBandHashSet(){
        initTime = new Date();
        data = new HashSet<>();
        nameComparator = Comparator.comparing(MusicBand::getName);
        readWriteLock = new ReentrantReadWriteLock();
    }

    public MusicBandHashSet(List<MusicBand> list){
        this();
        addAll(list);
    }

    public Comparator<MusicBand> getNameComparator() {
        return nameComparator;
    }

    public void add(MusicBand newElem){
        data.add(newElem);
    }

    public void addAll(List<MusicBand> list){
        data.addAll(list);
    }

    public void reload(List<MusicBand> list){
        data.clear();
        data.addAll(list);
    }

    /**
     * contains: collection type, count of elements, the date of the initialization
     * @return information of {@code MusicBandHashSet} object in a {@code String}
     */
    public String getInfo(){
        return "Collection type: " + data.getClass().toString() + ", count of elements: " + data.size()
                + ", init time: " + initTime;
    }

    public HashSet<MusicBand> getData(){
        return data;
    }

    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
