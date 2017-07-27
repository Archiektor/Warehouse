package com.example.archiektor.warehouse;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by Archiektor on 09.05.2017.
 */

public class Bolts extends Supply {

    public Bolts(String name) {
        super(name);
        setPathToDrawable(R.drawable.bolt);
    }

}
