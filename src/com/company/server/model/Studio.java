package com.company.server.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Studio implements Comparable<Studio>, Serializable {//Comparable

    private static final long serialVersionUID = 1L;

    @Column(name = "studio_name")
    private String name; /**Поле может быть null*/

    @Column(name = "studio_address")
    private String address; /**Поле не может быть null*/

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    @Override
    public String toString() {
        return "Studio " +
                "название: " + name +
                ", адрес: " + address;
    }

    @Override
    public int compareTo(Studio o) {
        if (o == null) return 1;
        return name.compareTo((o).name);
    }
}
