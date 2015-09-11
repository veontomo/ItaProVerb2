package com.veontomo.itaproverb.activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbDay;
import com.veontomo.itaproverb.fragments.FragManagerPanel;
import com.veontomo.itaproverb.fragments.FragShowSingle;

public class ActProverbDay extends AppCompatActivity implements FragManagerPanel.ManagerPanelActions {

    /**
     * A gesture detector
     */
    private GestureDetectorCompat mDetector;
    /**
     * Gesture listener that
     */
    private CustomGestureListener mGestureListener;

    /**
     * Fragments that manages single proverb visualisation.
     */
    private FragShowSingle mProverbFragment;

    private View mProverbFragmentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!Config.PRODUCTION_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
//                    .detectNetwork()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_proverb_day);
    }

    @Override
    public void onStart() {
        super.onStart();
        mProverbFragment = (FragShowSingle) getSupportFragmentManager().findFragmentById(R.id.act_proverb_day_frag_proverb);
        if (mProverbFragment != null) {
            ProverbDay provider = new ProverbDay();
            Proverb proverb = provider.todayProverb();
            mProverbFragment.load(proverb);
            mProverbFragment.updateView();

            this.mGestureListener = new CustomGestureListener();
            this.mDetector = new GestureDetectorCompat(this, mGestureListener);

            this.mProverbFragmentView = this.mProverbFragment.getView();
            if (this.mProverbFragmentView != null) {
                this.mProverbFragment.getView().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, final MotionEvent event) {
                        mDetector.onTouchEvent(event);
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public void onStop() {
        if (this.mProverbFragmentView != null) {
            this.mProverbFragmentView.setOnTouchListener(null);
            this.mProverbFragmentView = null;
        }
        this.mDetector = null;
        this.mGestureListener = null;
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proverb_day, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStatusChange() {
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented") ;

    }

    @Override
    public void onEdit() {
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented") ;
    }

    @Override
    public void onShare() {
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented") ;
    }

    @Override
    public void onDelete() {
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented") ;
    }


    /**
     * Gesture listener that detects fling-like gestures and calls hosting activity methods
     * when the gesture is detected.
     */
    class CustomGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            float dx = event2.getX() - event1.getX();
            if (dx > 0) {
                showOlderProverb();
            } else {
                showNewerProverb();
            }
            return true;
        }
    }

    /**
     * Retrieves older proverb of the day and passes it to {@link FragShowSingle} fragment.
     */
    private void showOlderProverb() {
        /// TODO
        Log.i(Config.APP_NAME, "method showOlderProverb has yet to be implemented.");
    }

    /**
     * Retrieves newer proverb of the day and passes it to {@link FragShowSingle} fragment.
     */
    private void showNewerProverb() {
        /// TODO
        Log.i(Config.APP_NAME, "method showNewerProverb has yet to be implemented.");
    }
}
