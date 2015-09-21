package com.veontomo.itaproverb.api;

import android.content.Context;

import com.veontomo.itaproverb.tasks.ProverbLoaderTask;

/**
 * Initializes the application.
 *
 */
public class AppInit {
    /**
     * Initializes the application.
     * <p>Reads the proverbs from a file that is supposed to be in the assets folder.</p>
     *
     * @param context
     * @param fileName name of the file to read from
     * @param encoding stream encoding
     */
    public static void loadProverbs(final Context context, final String fileName, final String encoding){
        /// TODO: if this class contains just one method and it is executed just once,
        /// consider moving this method where it is called and then get rid of this class.
        ProverbLoaderTask task = new ProverbLoaderTask(context, fileName, encoding);
        task.execute();
    }

}
