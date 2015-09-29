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
    public static void start(final Context context) {
        Log.i(Config.APP_NAME, "start alarm manager");

        SharedPreferences pm = PreferenceManager.getDefaultSharedPreferences(context);
        final boolean enable_notification = pm.getBoolean(context.getString(R.string.enable_notification_token), Config.NOTIFICATION_AUTO_START);
        Log.i(Config.APP_NAME, "start notification? " + enable_notification);
        if (!enable_notification) {
            return;
        }
        final long startTime = pm.getLong(context.getString(R.string.notification_time_token), System.currentTimeMillis() + Config.NOTIFICATION_TIME_OFFSET);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
            am.cancel(pendingIntent);
            Log.i(Config.APP_NAME, "broadcast is cancelled");
        }
        Log.i(Config.APP_NAME, "creating new broadcast that starts at " + startTime);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC, startTime, Config.FREQUENCY, pendingIntent);
    }

}
