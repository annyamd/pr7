package com.company.server.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

public enum MusicGenre implements Serializable {
    ROCK,
    RAP,
    SOUL,
    MATH_ROCK;

    private static final long serialVersionUID = 1L;

}