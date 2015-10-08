package com.veontomo.itaproverb.api;

import android.test.AndroidTestCase;

/**
 * Created by Mario Rossi on 08/10/2015 at 12:19.
 *
 * @author veontomo@gmail.com
 * @since xx.xx
 */
public class ProverbAdapterTest extends AndroidTestCase {
    ProverbAdapter adapter;

    public void setUp(){
        adapter = new ProverbAdapter(getContext(), null, 1f);
    }


    public void testInsertNothingInThree() {
        int[] result  = adapter.createDeterMapping(3, 0);
        assertEquals("resulting array must have 3 elements", 3, result.length);
        assertEquals("resulting array must have 0 at first position", 0, result[0]);
        assertEquals("resulting array must have 1 at second position", 1, result[1]);
        assertEquals("resulting array must have 2 at third position", 2, result[2]);
    }

    public void testInsertOneInTwo() {
        int[] result  = adapter.createDeterMapping(2, 1);
        assertEquals("resulting array must have 3 elements", 3, result.length);
        assertEquals("resulting array must have 0 at first position", 0, result[0]);
        assertEquals("resulting array must have 1 at second position", 1, result[1]);
        assertEquals("resulting array must have -1 at third position", -1, result[2]);
    }

    public void testInsertThreeInFive() {
        int[] result  = adapter.createDeterMapping(5, 3);
        assertEquals("resulting array must have 8 elements", 8, result.length);
        assertEquals("resulting array must have 0 at first position", 0, result[0]);
        assertEquals("resulting array must have 1 at second position", 1, result[1]);
        assertEquals("resulting array must have 2 at third position", 2, result[2]);
        assertEquals("resulting array must have -1 at fourth position", -1, result[3]);
        assertEquals("resulting array must have 3 at fifth position", 3, result[4]);
        assertEquals("resulting array must have -1 at sixth position", -1, result[5]);
        assertEquals("resulting array must have 4 at seventh position", 4, result[6]);
        assertEquals("resulting array must have -1 at eighth position", -1, result[7]);
    }

    public void testInsertTwoInSix() {
        int[] result  = adapter.createDeterMapping(6, 2);
        assertEquals("resulting array must have 8 elements", 8, result.length);
        assertEquals("resulting array must have 0 at first position", 0, result[0]);
        assertEquals("resulting array must have 1 at second position", 1, result[1]);
        assertEquals("resulting array must have 2 at third position", 2, result[2]);
        assertEquals("resulting array must have -1 at fourth position", -1, result[3]);
        assertEquals("resulting array must have 3 at fifth position", 3, result[4]);
        assertEquals("resulting array must have 4 at sixth position", 4, result[5]);
        assertEquals("resulting array must have 5 at seventh position", 5, result[6]);
        assertEquals("resulting array must have -1 at eighth position", -1, result[7]);
    }




}