package com.veontomo.itaproverb.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Logger;
import com.veontomo.itaproverb.api.Notificator;
import com.veontomo.itaproverb.fragments.NotificationTimeFragment;

import java.util.List;

/**
 * Settings activity
 */
public class ActSettings extends PreferenceActivity {
    /**
     * whether the notification status must be changed
     */
    private boolean change_notification_status = false;

    private String enable_notification_token;

    SharedPreferences preferences;

    SharedPreferences.OnSharedPreferenceChangeListener listener;
    private String marker = "ActSettings: ";

    /**
     * Populate the activity with the top-level headers.
     */
    @Override
    public void onBuildHeaders(List<Header> target) {
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        loadHeadersFromResource(R.xml.preference_headers, target);
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        final Context context = getApplicationContext();
        enable_notification_token = context.getString(R.string.enable_notification_token);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            /**
             * Called when a shared preference is changed, added, or removed. This
             * may be called even if a preference is set to its existing value.
             * <p/>
             * <p>This callback will be run on your main thread.
             *
             * @param sharedPreferences The {@link SharedPreferences} that received
             *                          the change.
             * @param key               The key of the preference that was changed, added, or
             */
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (enable_notification_token.equals(key)) {
                    change_notification_status = !change_notification_status;
                }
            }
        };
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected boolean isValidFragment (String fragmentName) {
        return NotificationTimeFragment.class.getName().equals(fragmentName);
    }


    @Override
    protected void onPause() {
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        if (change_notification_status) {
            changeNotificationStatus(preferences);
        }
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
        listener = null;
        preferences = null;
        super.onPause();
    }

    /**
     * Change the notification status
     */
    private void changeNotificationStatus(SharedPreferences preferences) {
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        final boolean shouldStart = preferences.getBoolean(enable_notification_token, Config.NOTIFICATION_AUTO_START);
        final Context context = getApplicationContext();
        if (shouldStart) {
            Logger.i("should start service");
            String notificationTimeToken = context.getString(R.string.notification_time_token);
            long currentTime = System.currentTimeMillis();
            long startTime = preferences.getLong(notificationTimeToken, currentTime + Config.NOTIFICATION_TIME_OFFSET);
            Notificator.start(context, startTime, Config.FREQUENCY);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(enable_notification_token, true);
            editor.apply();
        } else {
            Logger.i("should stop service");
            Notificator.stop(context);
        }

    }


}
