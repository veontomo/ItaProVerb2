package com.veontomo.itaproverb.api;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Initiate notification broadcast.
 */
public class Notificator {

    /**
     * Starts the notification service if it is not already running.
     *
     * @param context
     * @param startTime time at which the first notification should start
     */
    public static void start(final Context context, long startTime) {
        Logger.i("I am asked to start the service");

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);

        Logger.i("current time is " + System.currentTimeMillis());
        if (pendingIntent == null) {
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Logger.i("creating new broadcast that starts at " + startTime);
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            am.setRepeating(AlarmManager.RTC, startTime, Config.FREQUENCY, pendingIntent);
            am.cancel(pendingIntent);
        } else {
            Logger.i("broadcast is present");
        }
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
