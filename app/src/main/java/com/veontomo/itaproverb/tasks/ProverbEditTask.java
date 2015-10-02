package com.veontomo.itaproverb.tasks;

import android.os.AsyncTask;

import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Logger;
import com.veontomo.itaproverb.api.Storage;

/**
 * Edit existing or create new proverb.
 */
public class ProverbEditTask extends AsyncTask<Void, Void, Integer> {
    private final Storage storage;
    /**
     * proverb status
     */
    private final boolean status;
    /**
     * proverb text
     */
    private final String text;

    public ProverbEditTask(final Storage storage, String text, boolean status){
        this.status = status;
        this.text = text;
        this.storage = storage;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return storage.createProverb(text, status);
    }

    @Override
    public void onPostExecute(Integer id){
        Logger.i("id =" + id);
    }
}
