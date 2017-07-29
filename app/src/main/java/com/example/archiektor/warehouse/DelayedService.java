package com.example.archiektor.warehouse;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.util.Random;

import static com.example.archiektor.warehouse.DatabaseScheme.*;


public class DelayedService extends IntentService {

    public static int NOTIFICATION_ID;
    private DatabaseHelper databaseHelper;
    private Cursor cursor;
    private SQLiteDatabase db;
    private String remindText;

    public DelayedService() {
        super("DelayedService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getWritableDatabase();

            int count = getProfilesCount();

            for (int i = 1; i < count + 1; i++) {
                cursor = db.query(SuppplyTable.TABLE_NAME, new String[]{Cols.NAME, Cols.QUANTITY, Cols.CONDITION, Cols.IMAGE_ID},
                        "_id = ?", new String[]{Integer.toString(i)}, null, null, null);
                if (cursor.moveToFirst()) {
                    remindText = cursor.getString(0);
                    int quantity = Integer.parseInt(cursor.getString(1));
                    int condition = Integer.parseInt(cursor.getString(2));
                    if (quantity < condition) {
                        showReminder(remindText + " need to buy !!!");
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "new service condition fail", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "database1 with cursor unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void showReminder(final String text) {
        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT
                );
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setContentText(text)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Random random = new Random();
        NOTIFICATION_ID = random.nextInt(9999 - 1000) + 1000;

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public int getProfilesCount() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + SuppplyTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        return cnt;
    }
}
