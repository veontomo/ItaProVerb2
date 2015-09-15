package com.veontomo.itaproverb.tasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.veontomo.itaproverb.api.Storage;

/**
 * Sets proverb status.
 */
public class ProverbSetStatusTask extends AsyncTask<Integer, Void, Boolean> {
    /**
     * status od the proverb that should be set
     */
    private final boolean status;
    /**
     * Storage responsible for persisting data
     */
    private final Storage storage;

    public ProverbSetStatusTask(Storage storage, boolean status){
        this.storage = storage;
        this.status = status;

    }
    /**
     * Sets the {@link #status} of the proverb with given id.
     * <p>For the moment, only the first argument is considered.</p>
     * @param ids
     * @return
     */
    @Override
    protected Boolean doInBackground(@NonNull Integer... ids) {
        if (this.status){
            return storage.addToFavorites(ids[0]);
        }
        return storage.removeFromFavorites(ids[0]);
    }
}
