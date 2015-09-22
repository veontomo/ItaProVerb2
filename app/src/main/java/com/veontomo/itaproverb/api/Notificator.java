package com.veontomo.itaproverb.api;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.veontomo.itaproverb.AlarmReceiver;

/**
 * Initiate notification broadcast.
 */
public class Notificator {
    public static void start(final Context context) {
        Log.i(Config.APP_NAME, "start alarm manager");
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent == null) {
            Log.i(Config.APP_NAME, "creating new broadcast");
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), Config.PROVERB_DAY_FREQUENCY, pendingIntent);
        } else {
            Log.i(Config.APP_NAME, "broadcast already exist");
        }
    }

}
