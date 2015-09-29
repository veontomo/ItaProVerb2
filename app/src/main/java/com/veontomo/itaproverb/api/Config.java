package com.veontomo.itaproverb.api;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.veontomo.itaproverb.tasks.ProverbLoaderTask;

/**
 * Configuration parameters of the app.
 */
public abstract class Config {
    /**
     * Time in milliseconds that should pass after the app start for the proverb-a-day notification
     * to start in case its has not been scheduled so far.
     */
    public static final long NOTIFICATION_TIME_OFFSET = 10 * 60 * 1000;

    /**
     * Whether the notification should start if the app is launched for the first time
     */
    public static final boolean NOTIFICATION_AUTO_START = true;
    /**
     * Whether this is a first run of the app (in current session)
     */
    private static boolean FIRST_EXEC = true;
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
     * an url created by facebook that redirects to google play
     */
    public static final String FACEBOOK_URL = "https://fb.me/615874071885282";
    /**
     * location of the app logo
     */
    public static final String LOGO_URL = "https://lh3.googleusercontent.com/yDsAof472u9GZYaw0nQkS4p5_odYkuneSKVjvbkBEXO9UGOJBE8HdsjkiKFuGuwClg=w300";

    /**
     * Format in which it is represented time in today's proverb table.
     */
    public static final String DATE_FORMAT_STORAGE = "yyyy MM dd";

    /**
     * Size of sequence of consecutive records in proverb-of-day table within which
     * all proverbs must be different.
     */
    public static final int TODAY_MIN_CYCLE = 10;


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

    /**
     * Period in milliseconds during with a proverb-of-day notification should be fired off.
     */
    public final static int FREQUENCY = PRODUCTION_MODE ? 24 * 60 * 60 * 1000 : 10 * 60 * 1000;


    /**
     * Initializes the application.
     * <p>Reads the proverbs from a file that is supposed to be in the assets folder.</p>
     *
     * @param context
     */
    public static void loadProverbs(final Context context){
        if (Config.FIRST_EXEC) {
            ProverbLoaderTask task = new ProverbLoaderTask(context, PROVERB_SRC, ENCODING);
            task.execute();
            Config.FIRST_EXEC = false;
            Log.i(APP_NAME, "first execution");
        } else {
            Log.i(APP_NAME, "not a first execution");

        }
    }

}
