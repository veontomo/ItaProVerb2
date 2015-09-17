package com.veontomo.itaproverb.tasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.veontomo.itaproverb.activities.ActMultiBase;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs search among list of proverbs by given keyword.
 */
public class ProverbSearchTask extends AsyncTask<String, Void, List<Proverb>> {
    /**
     * list of proverbs to search within
     */
    private final List<Proverb> data;
    /**
     * Activity that has called this task
     */
    private final ActMultiBase caller;

    public ProverbSearchTask(List<Proverb> data, ActMultiBase caller) {
        this.data = data;
        this.caller = caller;
    }

    @Override
    protected List<Proverb> doInBackground(@NonNull String... params) {
        List<Proverb> result = new ArrayList<>();

        if (params.length > 0) {
            Log.i(Config.APP_NAME, "searching " + params[0]);
            for (Proverb p : data) {
                if (p.text.contains(params[0])) {
                    result.add(p);
                }
            }
        }
        return result;
    }

    @Override
    public void onPostExecute(List<Proverb> proverbs){
        caller.displayProverbs(proverbs);
    }

}
