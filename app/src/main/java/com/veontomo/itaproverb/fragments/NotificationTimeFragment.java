package com.veontomo.itaproverb.fragments;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.veontomo.itaproverb.R;

/**
 * This fragment shows the preferences for the first header.
 * https://github.com/commonsguy/cw-omnibus/tree/master/Prefs/SingleHeader2/src/com/commonsware/android/pref1header
 */
public class NotificationTimeFragment extends PreferenceFragment {

    /**
     * whether the notification status must be changed
     */
    private boolean change_notification_status = false;

    private String enable_notification_token;
    SharedPreferences preferences;

    SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make sure default values are applied.  In a real app, you would
        // want this in a shared function that is used to retrieve the
        // SharedPreferences wherever they are needed.
        PreferenceManager.setDefaultValues(getActivity(), R.xml.notification_settings, true);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.notification_time);

        enable_notification_token = getActivity().getString(R.string.enable_notification_token);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

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
}
