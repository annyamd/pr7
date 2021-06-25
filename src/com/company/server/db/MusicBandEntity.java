package com.company.server.db;

import com.company.server.model.MusicBand;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MUSIC_BANDS")
public class MusicBandEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE
//            , generator = "id_gen_2"
    )
//    @SequenceGenerator(name = "id_gen_2", sequenceName = "id_seq_2")
    @Column(name = "kit_id")
    private long id;

    @Embedded
    private MusicBand musicBand;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "login")
    public User creator;

    MusicBandEntity(MusicBand musicBand, User creator){
        this.creator = creator;
        this.musicBand = musicBand;
    }

    MusicBandEntity(){

    }


    public MusicBand getMusicBand() {
        if (id>0) musicBand.setId(id);
        return musicBand;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicBand = musicBand;
    }

    public void setId(long id) {
        if (musicBand != null) musicBand.setId(id);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "MusicBandEntity{" +
                "id=" + id +
                ", musicBand=" + musicBand +
                ", creator=" + creator +
                '}';
    }
}
