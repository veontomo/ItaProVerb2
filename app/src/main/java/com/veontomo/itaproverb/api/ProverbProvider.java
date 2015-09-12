package com.veontomo.itaproverb.api;

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

    public ProverbProvider(Storage storage){
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
     * @return
     */
    public List<Proverb> getAllProverbs() {
        return mStorage.getAllProverbs();
    }
}
