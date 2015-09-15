package com.veontomo.itaproverb.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.veontomo.itaproverb.activities.ActMultiBase;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.Storage;
import com.veontomo.itaproverb.fragments.FragShowMulti;

import java.util.List;

/**
 * Retrieves proverbs from database.
 */
public class ProverbRetrievalTask extends AsyncTask <Void, Void, List<Proverb>>{
    /**
     * object that performs the retrieval of required proverbs from the database
     */
    private final Storage storage;
    /**
     * fragment that should handle the output of concurrent operations.
     */
    private final FragShowMulti holder;

    /**
     * Whether the task should retrieve only favorite proverbs
     */
    private final boolean onlyFavorites;
    /**
     * An activity that stores found results in order to avoid multiple execution of this task.
     * If not set, the results are not stored anywhere, hence the task should be called each time
     * when the result is required.
     */
    private ActMultiBase cache;

    public ProverbRetrievalTask(Storage storage, FragShowMulti fragment, boolean onlyFavorites) {
        this.storage = storage;
        this.holder = fragment;
        this.onlyFavorites = onlyFavorites;

    }

    @Override
    protected List<Proverb> doInBackground(Void... params) {
        List<Proverb> proverbs;
        if (this.onlyFavorites){
            proverbs =  storage.getFavorites();
        } else {
            proverbs =  storage.getAllProverbs();
        }
        if (this.cache != null){
            this.cache.extractFromMultiProverbs(proverbs);
        }
        return proverbs;
    }

    @Override
    public void onPostExecute(List<Proverb> result){
        this.holder.load(result);
        this.holder.updateView();
    }


    /**
     * An activity that stores the results of execution of this concurrent operation.
     *
     * There is an activity {@link ActMultiBase} that starts this task. In order to prevent
     * that it starts this task every time it needs the result (when the device configs change),
     * let us save the result in the activity internal state.
     *
     *
     * @param cache
     */
    public void cache(final ActMultiBase cache) {
        this.cache = cache;
    }
}
