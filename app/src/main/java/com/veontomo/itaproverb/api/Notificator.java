package com.veontomo.itaproverb.api;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.veontomo.itaproverb.AlarmReceiver;

/**
 * Initiate notification broadcast.
 */
public class Notificator {

    /**
     * Starts the notification service.
     * <p/>
     * If the service is running, kill it and restart with new parameters (from shared preferences).
     *
     * @param context
     * @param startTime time at which the first notification should start
     */
    public static void start(final Context context, long startTime) {
        Logger.i("starting service");

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
            am.cancel(pendingIntent);
            Logger.i("broadcast is cancelled");
        }
        Logger.i("creating new broadcast that starts at " + startTime);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC, startTime, Config.FREQUENCY, pendingIntent);
    }

    /**
     * Stops the notification service if it is running. If it is not running, do nothing.
     *
     * @param context
     */
    public static void stop(Context context) {
        Logger.i("stopping service");
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
            am.cancel(pendingIntent);
            Logger.i("broadcast is cancelled");
        }

    }
}
