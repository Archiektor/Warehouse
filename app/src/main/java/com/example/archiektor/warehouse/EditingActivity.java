package com.example.archiektor.warehouse;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Objects;

import static com.example.archiektor.warehouse.DatabaseScheme.*;

public class EditingActivity extends Activity implements View.OnClickListener {

    private DatabaseHelper databaseHelper;
    private Cursor cursor;

    public static final String EXTRA_MESSAGE = "message";
    Toast toast;
    CharSequence text;
    int duration = Toast.LENGTH_SHORT;

    private TextView quantityNumber;
    private TextView condition;

    private Spinner spinner;

    private EditText editTextAdd;
    private EditText editTextMinus;

    private ImageButton addButton;
    private ImageButton minusButton;
    private Button setButton;

    private String firstHalfOfName;
    private String secondHalfOfName;
    private String nameOfSupply3;
    private String[] name;
    private int pathToDrawable;
    private int quantityAfterMinus;
    private int quantityAfterPlus;

    private HashSet<Supply> set = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        Intent intent = getIntent();
        nameOfSupply3 = intent.getStringExtra("message");

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        TextView nameOfSupplies = (TextView) findViewById(R.id.name_of_supply);

        name = nameOfSupply3.split("/");

        firstHalfOfName = name[0];
        secondHalfOfName = name[1];

        nameOfSupply3 = firstHalfOfName.substring(0, firstHalfOfName.length() - 1) + " " + secondHalfOfName;

        nameOfSupplies.setText(nameOfSupply3);

        switch (firstHalfOfName) {
            case "Bolts":
                Bolts bolt = new Bolts(nameOfSupply3);
                set.add(bolt);
                pathToDrawable = bolt.getPathToDrawable();
                quantityAfterMinus = bolt.getQuantity();
                break;
            case "Butterflynuts":
                Butterflynuts butterflynut = new Butterflynuts(nameOfSupply3);
                set.add(butterflynut);
                pathToDrawable = butterflynut.getPathToDrawable();
                quantityAfterMinus = butterflynut.getQuantity();
                break;
            case "Screws (metal)":
                MetalScrew metalScrew = new MetalScrew(nameOfSupply3);
                set.add(metalScrew);
                pathToDrawable = metalScrew.getPathToDrawable();
                quantityAfterMinus = metalScrew.getQuantity();
                break;
            case "Rivets":
                Rivets rivet = new Rivets(nameOfSupply3);
                set.add(rivet);
                pathToDrawable = rivet.getPathToDrawable();
                quantityAfterMinus = rivet.getQuantity();
                break;
            case "Spacers":
                Spacers spacer = new Spacers(nameOfSupply3);
                set.add(spacer);
                pathToDrawable = spacer.getPathToDrawable();
                quantityAfterMinus = spacer.getQuantity();
                break;
            case "Screws (wood)":
                Woodscrews woodscrew = new Woodscrews(nameOfSupply3);
                set.add(woodscrew);
                pathToDrawable = woodscrew.getPathToDrawable();
                quantityAfterMinus = woodscrew.getQuantity();
                break;
            default:
                imageView.setImageResource(R.drawable.warehouse);
                quantityNumber.setText(R.string.error);
                break;
        }

        imageView.setImageResource(pathToDrawable);

        quantityNumber = (TextView) findViewById(R.id.quantity_number);
        initializateDatabase(nameOfSupply3);
        //String quantityString = String.valueOf(quantityAfterMinus);
        //quantityNumber.setText(quantityString);

        spinner = (Spinner) findViewById(R.id.spinner);


        editTextAdd = (EditText) findViewById(R.id.editTextAdd);
        editTextMinus = (EditText) findViewById(R.id.editTextMinus);


        addButton = (ImageButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(this);
        minusButton = (ImageButton) findViewById(R.id.minus_button);
        minusButton.setOnClickListener(this);
        setButton = (Button) findViewById(R.id.button);
        setButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                for (Supply obj : set) {
                    if (Objects.equals(obj.getName(), nameOfSupply3)) {
                        Integer plusNumber = Integer.parseInt(editTextAdd.getText().toString());
                        //initializateDatabase(nameOfSupply3);
                        updateDatabase(nameOfSupply3, Integer.toString(plusNumber));
                        /*obj.setQuantity(plusNumber + obj.getQuantity());
                        quantityAfterPlus = obj.getQuantity();
                        String quantityString = String.valueOf(quantityAfterPlus);
                        quantityNumber.setText(quantityString);

                        editTextAdd.setText("");
                        editTextAdd.requestFocus();*/

                    }
                }
                break;
            case R.id.minus_button:
                for (Supply obj : set) {
                    if (Objects.equals(obj.getName(), nameOfSupply3)) {
                        Integer minusNumber = Integer.parseInt(editTextMinus.getText().toString());
                        obj.setQuantity(obj.getQuantity() - minusNumber);
                        quantityAfterMinus = obj.getQuantity();
                        String quantityString = String.valueOf(quantityAfterMinus);
                        quantityNumber.setText(quantityString);

                        editTextMinus.setText("");
                        editTextMinus.requestFocus();
                    }
                }
                break;
            case R.id.button:
                for (Supply obj : set) {
                    if (Objects.equals(obj.getName(), nameOfSupply3)) {
                        Integer selected = Integer.parseInt(spinner.getSelectedItem().toString());
                        // !!!!!!! obj.setConditionQuantity(selected);
                        updateDatabaseCond(nameOfSupply3, String.valueOf(selected));

//                        int conditionNumber = obj.getConditionQuantity();
//                        String quantityString = String.valueOf(conditionNumber);
//
//                        toast = Toast.makeText(v.getContext(), quantityString, duration);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
                    }
                }
                break;
        }
    }

    public void updateDatabase(String name, String quantity) {
        try {
            databaseHelper = new DatabaseHelper(this);
            // Gets the data repository in write modex
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            int dbQuaValue = makeCursor(db, name);
            int updatedQuaValue = dbQuaValue + Integer.parseInt(quantity);
            ContentValues values = new ContentValues();

            values.put(Cols.QUANTITY, String.valueOf(updatedQuaValue));

            db.update(SuppplyTable.TABLE_NAME, values, DatabaseScheme.Cols.NAME + " = ?", new String[]{name});

            cursor = db.query(SuppplyTable.TABLE_NAME, new String[]{Cols.NAME, Cols.QUANTITY, Cols.IMAGE_ID},
                    "name = ?", new String[]{name}, null, null, null);

            if (cursor.moveToFirst()) {
                //String nameText = cursor.getString(0);
                String quaText = cursor.getString(1);
                //int photoId = cursor.getInt(2);

                quantityNumber.setText(quaText);

            } else {
                Toast toast = Toast.makeText(this, "rrr", Toast.LENGTH_SHORT);
                toast.show();
            }

            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "database  unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    private void initializateDatabase(String name) {
        try {
            databaseHelper = new DatabaseHelper(this);
            // Gets the data repository in write modex
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            cursor = db.query(SuppplyTable.TABLE_NAME, new String[]{Cols.NAME, Cols.QUANTITY, Cols.IMAGE_ID},
                    "name = ?", new String[]{name}, null, null, null);

            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String quaText = cursor.getString(1);
                int photoId = cursor.getInt(2);

                quantityNumber.setText(quaText);

            } else {
                insertData(db, name, "0", "0", 0);
                quantityNumber.setText("0");
            }

            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast3 = Toast.makeText(this, "database with cursor unavailable", Toast.LENGTH_SHORT);
            toast3.show();
        }
    }

    private static void insertData(SQLiteDatabase db, String name, String quantity, String condition, int resourceId) {

        ContentValues values = new ContentValues();

        values.put(Cols.NAME, name);
        values.put(Cols.QUANTITY, quantity);
        values.put(Cols.CONDITION, condition);
        values.put(Cols.IMAGE_ID, resourceId);

        db.insert(SuppplyTable.TABLE_NAME, null, values);
    }

    private int makeCursor(SQLiteDatabase db, String name) {
        String quaText = "0";
        try {
            cursor = db.query(SuppplyTable.TABLE_NAME, new String[]{Cols.NAME, Cols.QUANTITY, Cols.IMAGE_ID},
                    "name = ?", new String[]{name}, null, null, null);

            if (cursor.moveToFirst()) {
                quaText = cursor.getString(1);
            } else {
                Toast toast = Toast.makeText(this, "stroke 291", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "stroke 296", Toast.LENGTH_SHORT);
            toast.show();
        }
        return Integer.parseInt(quaText);
    }

    public void updateDatabaseCond(String name, String condition) {
        try {
            databaseHelper = new DatabaseHelper(this);
            // Gets the data repository in write modex
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(Cols.CONDITION, String.valueOf(condition));

            db.update(SuppplyTable.TABLE_NAME, values, DatabaseScheme.Cols.NAME + " = ?", new String[]{name});

            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "database  unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}


