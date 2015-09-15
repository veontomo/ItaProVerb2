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
     * An activity that stores found results in order to avoid multiple execution of this task.
     * If not set, the results are not stored anywhere, hence the task should be called each time
     * when the result is required.
     */
    private ActMultiBase cache;

    public ProverbRetrievalTask(Storage storage, FragShowMulti fragment, ActMultiBase cache) {
        this.storage = storage;
        this.holder = fragment;
        this.cache = cache;

    }

    @Override
    protected List<Proverb> doInBackground(Void... params) {
        List<Proverb> proverbs = cache.getItems(storage);
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

}