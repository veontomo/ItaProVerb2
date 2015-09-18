package com.veontomo.itaproverb.api;

import android.os.StrictMode;
import android.util.Log;

/**
 * Configuration parameters of the app.
 */
public abstract class Config {
    /**
     * Whether the app is in production or not.
     */
    public static final boolean PRODUCTION_MODE = false;

    /**
     * a tag to mark the app
     */
    public static final String APP_NAME = "ItaProVerb";

    /**
     * name of the file that contains the proverbs in plain text format.
     */
    public static final String PROVERB_SRC = "proverbs.txt";

    /**
     * Encoding of file {@link #PROVERB_SRC} that contains proverbs.
     */
    public static final String ENCODING = "UTF-8";

    /**
     * Url of the app at google play store
     */
    public static final String GOOGLE_PLAY_STORE = "https://play.google.com/store/apps/details?id=com.veontomo.itaproverb";


    /**
     * Strict mode initialisation.
     */
    public final static void strictModeInit() {
        Log.i(APP_NAME, "Strict mode is initialized");
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

}
