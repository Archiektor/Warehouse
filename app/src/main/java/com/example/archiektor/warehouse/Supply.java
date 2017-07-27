package com.example.archiektor.warehouse;

/**
 * Created by Archiektor on 09.05.2017.
 */

public class Supply {

    private String name;
    private int quantity;
    private int conditionQuantity;
    private int pathToDrawable = R.mipmap.ic_launcher_round;

    public Supply(String name) {
        this.name = name;
    }

    public int getPathToDrawable() {
        return pathToDrawable;
    }

    public void setPathToDrawable(int pathToDrawable) {
        this.pathToDrawable = pathToDrawable;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getConditionQuantity() {
        return conditionQuantity;
    }

    public void setConditionQuantity(int conditionQuantity) {
        this.conditionQuantity = conditionQuantity;
    }
}
