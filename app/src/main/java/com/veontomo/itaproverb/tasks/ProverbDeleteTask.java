package com.veontomo.itaproverb.tasks;


import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.veontomo.itaproverb.api.Logger;
import com.veontomo.itaproverb.api.Storage;

/**
 * A task to delete a proverb.
 */
public class ProverbDeleteTask extends AsyncTask<Integer, Void, Boolean> {
    private final Storage storage;

    public ProverbDeleteTask(final Storage storage) {
        this.storage = storage;
    }

    /**
     * Deletes proverbs from the database. For the moment, only the first
     * argument is taken into consideration.
     *
     * @param params ids of proverbs to delete
     * @return boolean
     */
    @Override
    protected Boolean doInBackground(@NonNull Integer... params) {
        int id = params[0];
        return storage.removeProverb(id);
    }

    @Override
    public void onPostExecute(Boolean b) {
        Logger.i("proverb is deleted");
    }
}
