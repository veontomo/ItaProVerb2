package com.veontomo.itaproverb.activities;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.veontomo.itaproverb.R;

/**
 * Created by Mario Rossi on 06/10/2015 at 15:15.
 */
public class ActDispatchTest extends ActivityInstrumentationTestCase2<ActDispatch> {

    private ActDispatch mActivity;
    private TextView mAllProverb;
    private TextView mFavoriteProverb;
    private TextView mProverbDay;
    private TextView mProverbOracle;

    public ActDispatchTest() {
        super(ActDispatch.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Starts the activity under test using the default Intent with:
        // action = {@link Intent#ACTION_MAIN}
        // flags = {@link Intent#FLAG_ACTIVITY_NEW_TASK}
        // All other fields are null or empty.
        mActivity = getActivity();
        mAllProverb = (TextView) mActivity.findViewById(R.id.dispatcher_all_proverb);
        mFavoriteProverb = (TextView) mActivity.findViewById(R.id.dispatcher_favorites);
        mProverbDay = (TextView) mActivity.findViewById(R.id.dispatcher_proverb_day);
        mProverbOracle = (TextView) mActivity.findViewById(R.id.dispatcher_proverb_oracle);
    }

    /**
     * Test if your test fixture has been set up correctly. You should always implement a test that
     * checks the correct setup of your test fixture. If this tests fails all other tests are
     * likely to fail as well.
     */
    public void testPreconditions() {
        //Try to add a message to add context to your assertions. These messages will be shown if
        //a tests fails and make it easy to understand why a test failed
        assertNotNull("mActivity is null", mActivity);
        assertNotNull("All proverb placeholder is null", mAllProverb);
        assertNotNull("Favorite proverb placeholder is null", mFavoriteProverb);
    }

    /**
     * Tests the correctness of the all-proverb placeholder text.
     */
    public void testAllProverbs_Text() {
        final String expected = mActivity.getString(R.string.title_act_all);
        final String actual = mAllProverb.getText().toString();
        assertEquals("Dispatch activity contains wrong text for all-proverb placeholder", expected, actual);
    }

    /**
     * Tests the correctness of the favorite proverb placeholder text.
     */
    public void testFavoriteProverbs_Text() {
        final String expected = mActivity.getString(R.string.title_act_favorites);
        final String actual = mFavoriteProverb.getText().toString();
        assertEquals("Dispatch activity contains wrong text for favorite proverb placeholder", expected, actual);
    }


    /**
     * Tests the correctness of the proverb-of-a-day placeholder text.
     */
    public void testProverbOfDay_Text() {
        final String expected = mActivity.getString(R.string.title_act_proverb_day);
        final String actual = mProverbDay.getText().toString();
        assertEquals("Dispatch activity contains wrong text for proverb-of-day placeholder", expected, actual);
    }

    /**
     * Tests the correctness of the random proverb placeholder text.
     */
    public void testProverbOracle_Text() {
        final String expected = mActivity.getString(R.string.title_act_proverb_oracle);
        final String actual = mProverbOracle.getText().toString();
        assertEquals("Dispatch activity contains wrong text for random proverb placeholder", expected, actual);
    }

    /**
     * Tests that clicking the random proverb placeholder redirects to ActOracle activity.
     */
    public void testProverbOracle_Click() {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ActOracle.class.getName(), null, false);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProverbOracle.performClick();
            }
        });
        ActOracle nextActivity = (ActOracle) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 1000);

        assertNotNull("Click on oracle-placeholder must start an activity", nextActivity);
        nextActivity.finish();
    }

    /**
     * Tests that clicking the random proverb placeholder redirects to ActOracle activity.
     */
    public void testFavoriteProverb_Click() {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ActFavoriteProverbs.class.getName(), null, false);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFavoriteProverb.performClick();
            }
        });
        ActFavoriteProverbs nextActivity = (ActFavoriteProverbs) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 1000);

        assertNotNull("Click on favorite-proverb-placeholder must start an activity", nextActivity);
        nextActivity.finish();
    }


    /**
     * Tests that clicking the all-proverb placeholder redirects to ActOracle activity.
     */
    public void testAllProverb_Click() {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ActAllProverbs.class.getName(), null, false);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAllProverb.performClick();
            }
        });
        ActAllProverbs nextActivity = (ActAllProverbs) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 1000);

        assertNotNull("Click on all-proverb placeholder must start an activity", nextActivity);
        nextActivity.finish();
    }

    /**
     * Tests that clicking the proverb a day placeholder redirects to ActOracle activity.
     */
    public void testProverbDay_Click() {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ActProverbDay.class.getName(), null, false);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProverbDay.performClick();
            }
        });
        ActProverbDay nextActivity = (ActProverbDay) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 1000);

        assertNotNull("Click on proverb-a-day placeholder must start an activity", nextActivity);
        nextActivity.finish();
    }


}