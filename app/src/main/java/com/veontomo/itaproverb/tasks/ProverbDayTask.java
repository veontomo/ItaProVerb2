package com.veontomo.itaproverb.tasks;

import android.os.AsyncTask;

import com.veontomo.itaproverb.activities.ActSingleBase;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.Storage;


/**
 * Retrieves proverb from proverb-of-day table
 */
public class ProverbDayTask extends AsyncTask<Integer, Void, Proverb> {
    private final ActSingleBase caller;
    private final Storage storage;
    /**
     * whether the task is already running
     */
    public boolean isBusy = false;

    public ProverbDayTask(Storage storage, ActSingleBase caller) {
        this.storage = storage;
        this.caller = caller;
    }

    @Override
    protected Proverb doInBackground(Integer... params) {
        isBusy = true;
        int counter = 0;
        if (params.length > 0) {
            counter = params[0];
        }
        return storage.getTodayProverb(counter);
    }

    @Override
    public void onPostExecute(Proverb p) {
        if (p != null) {
            caller.loadItem(p);
        }
        isBusy = false;
    }
}
