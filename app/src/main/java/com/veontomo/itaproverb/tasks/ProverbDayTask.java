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

    public ProverbDayTask(Storage storage, ActSingleBase caller){
        this.storage = storage;
        this.caller = caller;
    }
    @Override
    protected Proverb doInBackground(Integer... params) {
        /// TODO: stub
        return new Proverb(1, new Date().toString(), true);
    }

    @Override
    public void onPostExecute(Proverb p){
        caller.loadItem(p);
    }
}
