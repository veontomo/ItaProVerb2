package com.veontomo.itaproverb.api;

/**
 * Proverb
 *
 * @since 0.1
 */
public class Proverb {
    /**
     * a number reserved for a proverb that has not been yet saved in a storage
     */
    public final static int NO_ID = -1;
    /**
     * Proverb text
     */
    public final String text;

    /**
     * identification number under which the proverb is saved in a storage
     */
    public final int id;

    /**
     * Constructor.
     * <p>To be used for creation of proverbs that have noty been yet saved in a storage.</p>
     * @param text proverb content
     */
    public Proverb(String text){
        this.id = NO_ID;
        this.text = text;
    }

    /**
     * Constructor
     * @param id  proverb id
     * @param text proverb content
     */
    public Proverb(int id, String text){
        this.id = id;
        this.text = text;
    }

}
