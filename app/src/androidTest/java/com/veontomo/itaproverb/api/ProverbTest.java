package com.veontomo.itaproverb.api;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Proverb-related unit tests
 *
 */
public class ProverbTest extends TestCase {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void gettingTextOfANewProverb() {
        Proverb proverb = new Proverb("When in Rome, do as the Romans.");
        assertEquals("Proverb text must be When in Rome, do as the Romans.", "When in Rome, do as the Romans.", proverb.text);
    }

    @Test
    public void gettingTextOfExistingProverb() {
        Proverb proverb = new Proverb(2, "Fortune favors the bold.");
        assertEquals("Proverb text must be Fortune favors the bold.", "Fortune favors the bold.", proverb.text);
    }

    @Test
    public void gettingIdOfANewProverb() {
        Proverb proverb = new Proverb("whatever");
        assertEquals("Proverb id must be -1", -1, proverb.id);
    }

    @Test
    public void gettingIdOfExistingProverb() {
        Proverb proverb = new Proverb(6, "whatever");
        assertEquals("Proverb id must be 6", 6, proverb.id);
    }



}