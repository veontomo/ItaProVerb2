package com.veontomo.itaproverb.api;

import junit.framework.TestCase;

/**
 * Created by Mario Rossi on 06/10/2015 at 11:53.
 *
 */
public class ProverbTest extends TestCase {

    public void testName() {
        Proverb p = new Proverb(1, "CHI CERCA TROVA", true);
        assertEquals("proverb text must be CHI CERCA TROVA", p.text, "CHI CERCA TROVA");
    }

    public void testIdExistingProverb() {
        Proverb p = new Proverb(43, "aaa", false);
        assertEquals("proverb id must be equal to 43", p.id, 43);
    }

    public void testIdNewProverb() {
        Proverb p = new Proverb("aaa", false);
        assertEquals("id of a new proverb must be equal to -1", p.id, -1);
    }

    public void testSetDateToNull() {
        Proverb p = new Proverb(4, "bbb", false);
        assertNull("date must be null if not set", p.date);
    }

    public void testSetDate() {
        Proverb p = new Proverb(4, "bbb", false, "06 Oct 2015");
        assertEquals("date must be set to 06 Oct 2015", p.date, "06 Oct 2015");
    }


}