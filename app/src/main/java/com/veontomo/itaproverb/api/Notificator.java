package com.veontomo.itaproverb.api;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.veontomo.itaproverb.AlarmReceiver;
import com.veontomo.itaproverb.R;

/**
 * Initiate notification broadcast.
 */
public class Notificator {

    /**
     * Starts the notification service.
     *
     * If the service is running, kill it and restart with new parameters (from shared preferences).
     * @param context
     */
    public static void start(final Context context) {
        Log.i(Config.APP_NAME, "starting service");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final long startTime = sharedPreferences.getLong(context.getString(R.string.notification_time_token), System.currentTimeMillis() + Config.NOTIFICATION_TIME_OFFSET);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
            am.cancel(pendingIntent);
            Log.i(Config.APP_NAME, "broadcast is cancelled");
        }
        Log.i(Config.APP_NAME, "creating new broadcast that starts at " + startTime);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC, startTime, Config.FREQUENCY, pendingIntent);
    }

    /**
     * Stops the notification service if it is running. If it is not running, do nothing.
     *
     * @param context
     */
    public static void stop(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
            am.cancel(pendingIntent);
            Log.i(Config.APP_NAME, "broadcast is cancelled");
        }

    }
}
