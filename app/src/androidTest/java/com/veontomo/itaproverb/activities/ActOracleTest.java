package com.veontomo.itaproverb.activities;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.veontomo.itaproverb.R;

import junit.framework.TestCase;

/**
 * Test suit for random proverb
 *
 */
public class ActOracleTest extends ActivityInstrumentationTestCase2<ActOracle> {

    private ActOracle mActivity;
    private View mEdit;

    public ActOracleTest() {
        super(ActOracle.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
        mEdit =  mActivity.findViewById(R.id.frag_manager_edit);
    }

    public void tearDown() throws Exception {

    }

    public void testPreconditions() {
        //Try to add a message to add context to your assertions. These messages will be shown if
        //a tests fails and make it easy to understand why a test failed
        assertNotNull("mActivity is null", mActivity);
        assertNotNull("Edit button is not found", mEdit);
    }


    public void testEdit(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ActEdit.class.getName(), null, false);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEdit.performClick();
            }
        });
        ActEdit nextActivity = (ActEdit) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 1000);

        assertNotNull("Click on all-proverb placeholder must start an activity", nextActivity);
        nextActivity.finish();
    }
}