package com.veontomo.itaproverb.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.tasks.ProverbLoaderTask;

/**
 * Performs initialization of the app (loads proverbs, starts services etc).
 */
public class Initializer {
    /**
     * a token that stores boolean value whether it is the very first application execution
     */
    private static final String FIRST_RUN_TOKEN = "first_run";

    /**
     * Initializes the application.
     * <p>Reads the proverbs from a file that is supposed to be in the assets folder.</p>
     *
     * @param context
     */
    public static void loadProverbs(final Context context) {
        ProverbLoaderTask task = new ProverbLoaderTask(context, Config.PROVERB_SRC, Config.ENCODING);
        task.execute();
    }

    public static final void init(final Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        boolean firstExecution = pref.getBoolean(FIRST_RUN_TOKEN, true);
        if (firstExecution) {
            loadProverbs(context);
            editor.putBoolean(FIRST_RUN_TOKEN, false);
        }
        // try to synchronize the notification service status what is stored in the preferences:
        // 1. if the preferences either state that the service is enabled or contain no info
        // regarding the service status, then try to start the service.
        // 2. if the preferences state that the service is disabled, then try to stop it.
        String enableNotificationToken = context.getString(R.string.enable_notification_token);
        boolean enableNotification = pref.getBoolean(enableNotificationToken, true);
        if (enableNotification){
            String notificationTimeToken = context.getString(R.string.notification_time_token);
            long startTime = pref.getLong(notificationTimeToken, System.currentTimeMillis() + Config.NOTIFICATION_TIME_OFFSET);
            Notificator.start(context, startTime);
            editor.putBoolean(enableNotificationToken, true);
            editor.putLong(notificationTimeToken, startTime);
        } else {
            Notificator.stop(context);
        }
        editor.apply();
    }
}
