package com.veontomo.itaproverb.tasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.veontomo.itaproverb.activities.ActMultiBase;
import com.veontomo.itaproverb.api.Proverb;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs search among list of proverbs by given keyword.
 */
public class ProverbSearchTask extends AsyncTask<String, Void, int[]> {
    /**
     * list of proverbs to search within
     */
    private final List<Proverb> data;
    /**
     * Activity that has called this task
     */
    private final ActMultiBase caller;

    public boolean isFree = true;

    public ProverbSearchTask(List<Proverb> data, ActMultiBase caller) {
        this.data = data;
        this.caller = caller;
    }

    @Override
    protected int[] doInBackground(@NonNull String... params) {
        isFree = false;
        int size;
        List<Integer> list = new ArrayList<>();
        if (params.length > 0) {
            size = data.size();
            for (int i = 0; i < size; i++) {
                if (data.get(i).text.contains(params[0])) {
                    list.add(i);
                }
            }
        }
        size = list.size();
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    @Override
    public void onPostExecute(int[] filter) {
        caller.setFilter(filter);
        caller.displayProverbs();
        isFree = true;
    }

}
