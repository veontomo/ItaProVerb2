package com.veontomo.itaproverb.api;

import android.util.Log;

/**
 * Custom logger.
 *
 */
public class Logger {
    /**
     * If in developer mode, the method is identical to {@link  Log#i} with the tag being
     * {@link Config@APP_NAME}. If in production mode, does nothing.
     * @param msg
     */
    public final static void i(String msg){
        if (!Config.PRODUCTION_MODE){
            Log.i(Config.APP_NAME, msg);
        }
    }
}
