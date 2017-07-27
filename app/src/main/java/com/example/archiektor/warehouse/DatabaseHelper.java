package com.example.archiektor.warehouse;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.icu.lang.UProperty.NUMERIC_TYPE;
import static com.example.archiektor.warehouse.DatabaseScheme.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "warehouse.db";

    private static final int DB_VERSION = 1;

    String deleteTable = "DROP TABLE IF EXISTS " + SuppplyTable.TABLE_NAME;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    //private static final String NUMERIC_TYPE = " NUMERIC";
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + SuppplyTable.TABLE_NAME + "( _id"
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Cols.NAME + TEXT_TYPE + COMMA_SEP
            + Cols.QUANTITY + TEXT_TYPE + COMMA_SEP
            + Cols.CONDITION + TEXT_TYPE + COMMA_SEP
            + Cols.IMAGE_ID + INTEGER_TYPE + ")";

    DatabaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL(DATABASE_CREATE);
        }

        if (oldVersion < 2) {
            /*ContentValues drinkValues = new ContentValues();
            drinkValues.put(DrinksScheme.Cols.DESCRIPTION, "Tasty");
            db.update(DrinksTable.TABLE_NAME, drinkValues, DrinksScheme.Cols.NAME + " = ?", new String[]{"Latte"});
            db.execSQL(addColumn);*/
        }

        if (oldVersion < 3 && oldVersion != 1) {
            /*ContentValues drinkValues = new ContentValues();
            drinkValues.put(DrinksScheme.Cols.DESCRIPTION, "Espresso and steamed milk");
            db.update(DrinksTable.TABLE_NAME, drinkValues, DrinksScheme.Cols.NAME + " = ?", new String[]{"Latte"});*/
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(deleteTable);
        updateMyDatabase(db, 0, DB_VERSION);
    }
}
