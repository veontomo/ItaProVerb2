package com.veontomo.itaproverb.activities;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

import com.veontomo.itaproverb.R;

/**
 * Test suit for random proverb
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
        mEdit = mActivity.findViewById(R.id.frag_manager_edit);
    }

    public void testPreconditions() {
        //Try to add a message to add context to your assertions. These messages will be shown if
        //a tests fails and make it easy to understand why a test failed
        assertNotNull("mActivity is null", mActivity);
        assertNotNull("Edit button is not found", mEdit);
    }

    public void testProverbTextFieldIsNotEmpty() {
        final TextView tv = (TextView) mActivity.findViewById(R.id.frag_show_single_text);
        final String text = tv.getText().toString();
        assertNotNull("Random proverb must display a text", text);
    }

    public void testEditButtonMustRedirectToEditPage() {
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

    /**
     * Scenario: clicking "edit" and then "back" buttons
     *     When I am in "Fortune teller" screen
     *     And I see a proverb text
     *     And I click "edit" button
     *     When "Edit Proverb" activity loads
     *     And I click "back" button
     *     When "Fortune teller" screen re-appears
     *     Then I should see the original proverb text
     */
    public void testClickEditAndTurnBack() {
        // getting initial content of the activity
        final TextView tv = (TextView) mActivity.findViewById(R.id.frag_show_single_text);
        final String originalText = tv.getText().toString();

        // launching edit activity
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ActEdit.class.getName(), null, false);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEdit.performClick();
            }
        });
        final ActEdit editActivity = (ActEdit) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 1000);

        // pressing "back" button when in edit activity in order to return to the original activity
        Instrumentation.ActivityMonitor activityMonitor2 = getInstrumentation().addMonitor(ActOracle.class.getName(), null, false);
        editActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editActivity.onBackPressed();
            }
        });
        // original activity recreated
        final ActOracle actRecreated = (ActOracle) getInstrumentation().waitForMonitorWithTimeout(activityMonitor2, 1000);
        // getting content of the recreated activity
        final TextView tv2 = (TextView) actRecreated.findViewById(R.id.frag_show_single_text);
        final String resultingText = tv2.getText().toString();

        assertEquals("Oracle activity should maintain the proverb text when clicking on edit and then coming back", originalText, resultingText);
    }
}