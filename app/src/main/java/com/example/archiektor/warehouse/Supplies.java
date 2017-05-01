package com.example.archiektor.warehouse;

import java.util.ArrayList;

/**
 * Created by Archiektor on 01.05.2017.
 */

public class Supplies {

    private String name;
    private Integer imageId;

    public Supplies(String name, Integer imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String toString() {
        return name;
    }


}
