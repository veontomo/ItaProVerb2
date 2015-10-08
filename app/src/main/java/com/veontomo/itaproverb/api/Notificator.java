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
     * Whether the alarm manager has already set the broadcast.
     * Returns true if the broadcast is set, false otherwise.
     *
     * @param context
     * @return
     */
    public static boolean isRunning(final Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }

    /**
     * Starts the notification service at given time.
     *
     * If the time of the first notification happens to be in the past, then the startTime
     * is shifted into a future by adding minimal multiple of frequency.
     *
     *
     * @param context
     * @param startTime time at which the first notification should start
     * @param frequency how often the notification should trigger
     */
    public static void start(final Context context, long startTime, long frequency) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        long currentTime = System.currentTimeMillis();
        if (startTime < currentTime) {
            int offset = ((int) ((currentTime - startTime)/frequency)) + 1 ;
            Logger.i("offset = " + offset);
            startTime += offset * frequency;
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Logger.i("starting the service at " + startTime);
        Logger.i("current time " + currentTime);
        am.setRepeating(AlarmManager.RTC, startTime, frequency, pendingIntent);
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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        am.cancel(pendingIntent);

    }
}
