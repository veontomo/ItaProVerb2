package com.veontomo.itaproverb.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mario Rossi on 09/09/2015 at 13:47.
 *
 * @author veontomo@gmail.com
 * @since xx.xx
 */
public class ProverbTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void idOfNonSavedProverbShoudBeMinusOne(){
        Proverb proverb = new Proverb("whatever");
        assertEquals(-1, proverb.id);
    }
}