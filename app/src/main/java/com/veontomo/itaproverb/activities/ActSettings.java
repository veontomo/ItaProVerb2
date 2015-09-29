package com.veontomo.itaproverb.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Notificator;

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

    /**
     * Populate the activity with the top-level headers.
     */
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(Config.APP_NAME, "on resume");
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
    protected void onPause() {
        if (change_notification_status) {
            changeNotificationStatus();
        }
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
        listener = null;
        preferences = null;
        super.onPause();
    }

    /**
     * Change the notification status
     */
    private void changeNotificationStatus() {
        boolean shouldStart = preferences.getBoolean(enable_notification_token, Config.NOTIFICATION_AUTO_START);
        if (shouldStart) {
            Notificator.start(getApplicationContext());
        } else {
            Notificator.stop(getApplicationContext());
        }

    }


    /**
     * This fragment shows the preferences for the first header.
     */
    public static class NotificationTimeFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Make sure default values are applied.  In a real app, you would
            // want this in a shared function that is used to retrieve the
            // SharedPreferences wherever they are needed.
            PreferenceManager.setDefaultValues(getActivity(), R.xml.notification_settings, true);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.notification_time);
        }
    }


}
