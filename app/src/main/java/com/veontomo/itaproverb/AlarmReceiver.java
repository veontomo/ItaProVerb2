package com.veontomo.itaproverb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.veontomo.itaproverb.api.Config;

/**
 * Created by Mario Rossi on 22/09/2015 at 14:30.
 *
 * @author veontomo@gmail.com
 * @since xx.xx
 */
public class AlarmReceiver extends BroadcastReceiver {

     /**
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Config.APP_NAME, "on receive");
    }
}
