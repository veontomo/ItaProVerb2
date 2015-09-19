package com.veontomo.itaproverb.api;

import android.util.Log;

import com.veontomo.itaproverb.activities.ActProverbDay;
import com.veontomo.itaproverb.activities.ActSingleBase;
import com.veontomo.itaproverb.tasks.ProverbDayTask;
import com.veontomo.itaproverb.tasks.ProverbDeleteTask;
import com.veontomo.itaproverb.tasks.ProverbEditTask;
import com.veontomo.itaproverb.tasks.ProverbSetStatusTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Performs proverb-related operations like following ones:
 * <br/> 1. change the proverb status (favorite/non-favorite)
 * <br/> 2. edit the proverb
 * <br/> 3. delete the proverb from the storage
 * <br/> 4. retrieve all proverbs
 * <br/> 5. retrieve favorite proverbs
 * <br/> 5. retrieve proverb of the day and related operations (newer/older)
 */
public class ProverbProvider {

    private final Storage mStorage;

    public ProverbProvider(Storage storage) {
        this.mStorage = storage;
    }

    /**
     * Returns proverb of today
     *
     * @return
     */
    public Proverb todayProverb() {
        /// TODO
        return randomProverb();
    }


    public Proverb randomProverb() {
        return mStorage.getRandomProverb();
    }

    /**
     * Returns favorite proverbs
     */
    public List<Proverb> favoriteProverbs() {
        return mStorage.getFavorites();
    }

    /**
     * Returns list of all proverbs (taking in consideration their statuses as well)
     *
     * @return
     */
    public List<Proverb> getAllProverbs() {
        return mStorage.getAllProverbs();
    }

    /**
     * Removes proverb with given id.
     *
     * @param id
     */
    public void deleteProverb(int id) {
        ProverbDeleteTask task = new ProverbDeleteTask(mStorage);
        task.execute(id);
    }

    /**
     * Updates text of proverb with given id.
     *
     * @param id   proverb id
     * @param text updated text of proverb
     */
    public void updateProverb(final int id, final String text) {
        Log.i(Config.APP_NAME, "new content of proverb " + id + " is " + text);
        mStorage.updateProverb(id, text);
    }

    /**
     * Sets the status of the proverb
     *
     * @param id     id of the proverb which status is to be set
     * @param status status to be set
     */
    public void setProverbStatus(final int id, final boolean status) {
        ProverbSetStatusTask task = new ProverbSetStatusTask(mStorage, status);
        task.execute(id);
    }

    /**
     * Create a new proverb (in database)
     *
     * @param text   proverb text
     * @param status whether the proverb is among favorites
     */
    public void createProverb(String text, boolean status) {
        Log.i(Config.APP_NAME, "new proverb: " + text + ", is it favorite? " + status);
        ProverbEditTask task = new ProverbEditTask(mStorage, text, status);
        task.execute();
    }

    /**
     * Retrieves older proverb from today's ones
     *
     * @param caller instance that should deal with the result of the execution
     */
    public void getOlder(ActSingleBase caller) {
        ProverbDayTask task = new ProverbDayTask(mStorage, caller);
        task.execute();
    }

    /**
     * Retrieves newer proverb from today's ones
     *
     * @param caller instance that should deal with the result of the execution
     */
    public void getNewer(ActSingleBase caller) {
        /// TODO
    }
}
