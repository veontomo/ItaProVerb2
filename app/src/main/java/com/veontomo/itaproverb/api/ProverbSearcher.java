package com.veontomo.itaproverb.api;

import com.veontomo.itaproverb.activities.ActMultiBase;
import com.veontomo.itaproverb.tasks.ProverbSearchTask;

import java.util.List;

/**
 * Accepts a keyword for further asynchronous search search.
 */
public class ProverbSearcher {

    /**
     * activity that has called this searcher
     */
    private final ActMultiBase caller;

    private ProverbSearchTask worker;


    public ProverbSearcher(ActMultiBase caller) {
        this.caller = caller;
    }

    /**
     * Find given keyword in a list of proverb
     *
     * @param keyword
     * @param items
     */
    public void find(String keyword, List<Proverb> items) {
        if (worker == null || worker.isFree) {
            worker = new ProverbSearchTask(items, caller);
            worker.execute(keyword);
        } else {
            Logger.i("I am busy");
        }
    }
}
