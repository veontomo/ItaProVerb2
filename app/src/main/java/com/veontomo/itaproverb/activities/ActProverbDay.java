package com.veontomo.itaproverb.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Logger;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbProvider;

/**
 * Displays a proverb of the day along with manager panel.
 */
public class ActProverbDay extends ActSingleBase {

    private static int counter = 1;
    private String marker = "ActProverbDay " + (counter++) + ": ";

    /**
     * title with which the proverb of the day is shared on a social network
     */
    @Override
    public String getSharePostTitle() {
        return getString(R.string.share_post_title_proverb_day);
    }


    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_proverb_day);
    }

    @Override
    public void onStart() {
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onStart();
        this.mDetector = new GestureDetectorCompat(this, new SwipeGestureListener(this, getProverbProvider()));
    }

    @Override
    public Proverb getItem(@NonNull ProverbProvider provider) {
        return provider.todayProverb();
    }

    @Override
    protected void registerListeners() {
        Fragment f = this.mFragItem;
        View v = null;
        if (f != null) {
            v = this.mFragItem.getView();
        }
        if (v != null) {
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(final View view, final MotionEvent event) {
                    mDetector.onTouchEvent(event);
                    return true;
                }
            });
        }
    }

    @Override
    protected void unregisterListeners() {
        Fragment f = this.mFragItem;
        View v = null;
        if (f != null) {
            v = this.mFragItem.getView();
        }
        if (v != null) {
            v.setOnClickListener(null);
        }
    }


    /**
     * Gesture listener that is attached to the view with the proverb
     */
    class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        /**
         * takes care of getting newer or older proverb
         */
        private final ProverbProvider provider;
        /**
         * instance that use this listener and to which {@link #provider} should
         * handle the result of execution
         */
        private final ActSingleBase caller;

        public SwipeGestureListener(final ActSingleBase caller, final ProverbProvider provider) {
            this.caller = caller;
            this.provider = provider;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            float dx = event2.getX() - event1.getX();
            if (dx > 0) {
                provider.getOlder(caller);
            } else {
                provider.getNewer(caller);
            }
            return true;
        }

    }

}
