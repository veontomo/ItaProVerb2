package com.veontomo.itaproverb.api;

/**
 * Proverb
 *
 * @since 0.1
 */
public final class Proverb {
    /**
     * a number reserved for a proverb that has not been yet saved in a storage
     */
    private final static int NO_ID = -1;
    /**
     * Proverb text
     */
    public final String text;

    /**
     * identification number under which the proverb is saved in a storage
     */
    public final int id;

    /**
     * Whether the proverb is among favorites or not.
     */
    public boolean isFavorite;

    /**
     * latest date when given proverb was proverb of the day
     */
    public final String date;

    /**
     * Constructor.
     * <p>To be used for creation of proverbs that have not been yet saved in a storage.</p>
     * @param text proverb content
     */
    public Proverb(String text, boolean isFavorite){
        this(NO_ID, text, isFavorite, null);
    }

    /**
     * Constructor
     * @param id  proverb id
     * @param text proverb content
     */
    public Proverb(int id, String text, boolean isFavorite){
        this(id, text, isFavorite, null);
    }

    /**
     * Constructor
     * @param id  proverb id
     * @param text proverb content
     * @param date date when the proverb was the proverb of the day for the most recent time
     */
    public Proverb(int id, String text, boolean isFavorite, String date){
        this.id = id;
        this.text = text;
        this.isFavorite = isFavorite;
        this.date = date;
    }


}
