package com.veontomo.itaproverb.api;

import android.content.Context;
import android.util.Log;

import com.veontomo.itaproverb.tasks.ProverbLoaderTask;

import java.io.InputStream;

/**
 * Initializes the application.
 *
 */
public class AppInit {

    /**
     * Initializes the storage database: reads given stream with given encoding.
     *
     * @param context
     * @param fileName name of the stream to read from
     * @param encoding stream encoding
     */
    public static void loadProverbs(final Context context, final String fileName, final String encoding){
        ProverbLoaderTask task = new ProverbLoaderTask(context, fileName, encoding);
        task.execute();
    }

}
