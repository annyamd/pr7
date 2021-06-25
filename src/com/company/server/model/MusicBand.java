package com.company.server.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

//пока сортирует только по id

@Embeddable
public class MusicBand implements Comparable<MusicBand>, Serializable {
    private static final long serialVersionUID = 1L;

//    @GeneratedValue(strategy=GenerationType.SEQUENCE
//            , generator = "id_gen_2"
//    )
//    @SequenceGenerator(name = "id_gen", sequenceName = "id_seq") //???
    @Column(name = "id")
    private long id; /**Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически*/

    private String name; /**Поле не может быть null, Строка не может быть пустой*/

    @Embedded
    private Coordinates coordinates; /**Поле не может быть null*/

    private LocalDateTime creationDate; /**Поле не может быть null, Значение этого поля должно генерироваться автоматически*/

    private int numberOfParticipants; /**Значение поля должно быть больше 0*/

    @Enumerated(EnumType.STRING)
    private MusicGenre genre; /**Поле может быть null*/

    @Embedded
    private Studio studio; /**Поле может быть null*/

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;
    }

    public void setNumberOfParticipants(int numberOfParticipants){
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setGenre(MusicGenre genre){
        this.genre = genre;
    }

    public void setStudio(Studio studio){
        this.studio = studio;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public int compareTo(MusicBand o) {
        if (o == null) return 2;
        if ((id - o.id) > 0) return 1;
        else if (id == o.id) return 0;
        else return -1;
    }

    @Override
    public int hashCode() {
        return ("" + id + name + coordinates +
                creationDate +
                numberOfParticipants + genre + studio).hashCode();
    }

    @Override
    public String toString() {
        return "MusicBand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", numberOfParticipants=" + numberOfParticipants +
                ", genre=" + genre +
                ", studio=" + studio +
                '}';
    }
}
