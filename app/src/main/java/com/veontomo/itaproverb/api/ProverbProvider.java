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

    /**
     * Returns proverb of today
     *
     * @return
     */
    public Proverb todayProverb() {
        /// TODO
        return new Proverb(443, "Chi cerca trova");
    }


    public Proverb randomProverb() {
        /// TODO
        Random randomGenerator = new Random();
        int id = randomGenerator.nextInt(1000);
        return new Proverb(id, "Random text: " + String.valueOf(randomGenerator.nextInt(88)));
    }

    /**
     * Returns favorite proverbs
     */
    public List<Proverb> favoriteProverbs() {
        /// TODO: current implementation serves just to return something that looks realistic
        List<Proverb> output = new ArrayList<>();
        output.add(randomProverb());
        output.add(randomProverb());
        output.add(randomProverb());
        output.add(randomProverb());
        return output;
    }
}
