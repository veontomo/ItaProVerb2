package com.veontomo.itaproverb.fragments;

/**
 * Created by Mario Rossi on 30/09/2015 at 16:17.
 *
 * @author veontomo@gmail.com
 * @since xx.xx
 */

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.veontomo.itaproverb.R;

/**
 * This fragment shows the preferences for the first header.
 */
public class NotificationTimeFragment extends PreferenceFragment {
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
