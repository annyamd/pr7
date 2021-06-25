package com.company.server.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Coordinates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "coordinates_x")
    private float x; /**Максимальное значение поля: 645*/

    @Column(name = "coordinates_y")
    private Integer y; /**Значение поля должно быть больше -328, Поле не может быть null*/

    public float getX() {
        return x;
    }

    public void setX(float x){
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y){
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
