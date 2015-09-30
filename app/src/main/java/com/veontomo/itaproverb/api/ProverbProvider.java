package com.veontomo.itaproverb.api;

import android.util.Log;

import com.veontomo.itaproverb.activities.ActSingleBase;
import com.veontomo.itaproverb.tasks.ProverbDayTask;
import com.veontomo.itaproverb.tasks.ProverbDeleteTask;
import com.veontomo.itaproverb.tasks.ProverbEditTask;
import com.veontomo.itaproverb.tasks.ProverbSetStatusTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    /**
     * number of the proverb to retrieve from the end of proverb-of-day table.
     * <br> 0 corresponds to the latest proverb of the day (let say, today's one,
     * 1 corresponds to yesterday's one etc.
     * <br> Must be non-negative.
     */
    private int proverbNumber = 0;

    /**
     * task that retrieves the proverbs from proverb-of-day table
     */
    private ProverbDayTask dayTask;

    public ProverbProvider(Storage storage) {
        this.mStorage = storage;
    }

    /**
     * Returns proverb of today. If it does not exist or if its date corresponds to
     * another day (different from today)
     *
     * @return
     */
    public Proverb todayProverb() {
        Proverb p = mStorage.getTodayProverb(0);
        String today = formatTodayDate(Config.DATE_FORMAT_STORAGE);
        if (p == null || !today.equals(p.date)) {
            p = assignProverbOfToday(today);
        }
        return p;
    }

    /**
     * Assign proverb for given date
     *
     * @return proverb of day
     */
    private Proverb assignProverbOfToday(String today) {
        Proverb p = mStorage.getRandomNonRepeating(Config.TODAY_MIN_CYCLE);
        if (p == null) {
            return null;
        }
        int id = mStorage.saveAsToday(p.id, today);
        if (id == -1) {
            return null;
        }
        return new Proverb(p.id, p.text, p.isFavorite, today);
    }

    /**
     * Returns a string corresponding to a today date formatted accordingly to
     * {@link Config#APP_NAME}
     *
     * @param format
     * @return
     */
    private String formatTodayDate(String format) {
        Calendar cal = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat(format);
        Date date = cal.getTime();
        return sdf.format(date);
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
        Log.i(Config.APP_NAME, "deleting ptoverb " + id);
        ProverbDeleteTask task = new ProverbDeleteTask(mStorage);
        task.execute(id);
    }

    /**
     * Updates text of proverb with given id.
     *
     * @param id   proverb id
     * @param text updated text of proverb
     */
    public void updateProverb(final int id, final String text, final boolean status) {
        /// TODO: check whether it is executed asynchronously.
        Log.i(Config.APP_NAME, "new content of proverb " + id + " is " + text);
        /// TODO: make use of return value of the updateProverb method
        mStorage.updateProverb(id, text, status);
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
        if (dayTask == null || !dayTask.isBusy) {
            proverbNumber++;
            dayTask = new ProverbDayTask(mStorage, caller);
            dayTask.execute(proverbNumber);
        }
    }

    /**
     * Retrieves newer proverb from today's ones
     *
     * @param caller instance that should deal with the result of the execution
     */
    public void getNewer(ActSingleBase caller) {
        if (proverbNumber <= 0) {
            Log.i(Config.APP_NAME, "already the latest proverb");
            return;
        }
        if (dayTask == null || !dayTask.isBusy) {
            proverbNumber--;
            dayTask = new ProverbDayTask(mStorage, caller);
            dayTask.execute(proverbNumber);
        }
    }
}
