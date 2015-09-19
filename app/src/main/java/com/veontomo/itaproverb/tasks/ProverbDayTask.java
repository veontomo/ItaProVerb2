package com.veontomo.itaproverb.tasks;

import android.os.AsyncTask;

import com.veontomo.itaproverb.activities.ActSingleBase;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.Storage;

import java.util.Date;

/**
 * Retrieves proverb from proverb-of-day table
 *
 */
public class ProverbDayTask extends AsyncTask<Integer, Void, Proverb> {
    private final ActSingleBase caller;
    private final Storage storage;
    /**
     * whether the task is already running
     */
    public boolean isBusy = false;

    public ProverbDayTask(Storage storage, ActSingleBase caller){
        this.storage = storage;
        this.caller = caller;
    }
    @Override
    protected Proverb doInBackground(Integer... params) {
        /// TODO: stub
        return new Proverb(1, (new Date().toString()) + params[0], params[0] %2  == 0);
    }

    @Override
    public void onPostExecute(Proverb p){
        caller.loadItem(p);
    }
}
