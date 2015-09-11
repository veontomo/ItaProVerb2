package com.veontomo.itaproverb.api;

import com.veontomo.itaproverb.api.Proverb;

import java.util.Random;

/**
 * Performs operations related with assigning and retrieval of
 * proverbs of the day.
 */
public class ProverbDay {

    /**
     * Returns proverb of today
     * @return
     */
    public Proverb todayProverb() {
        /// TODO
        return new Proverb(443, "Chi cerca trova");
    }


    public Proverb randomProverb(){
        /// TODO
        Random randomGenerator = new Random();
        int id = randomGenerator.nextInt(1000);
        return new Proverb(id, "Random text: " + String.valueOf(randomGenerator.nextInt(88)));
    }
}
