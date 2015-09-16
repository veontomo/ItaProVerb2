package com.veontomo.itaproverb.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbProvider;

/**
 * Displays a proverb of the day along with manager panel.
 */
public class ActProverbDay extends ActSingleBase {
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!Config.PRODUCTION_MODE) {
            Config.strictModeInit();
        }
        super.onCreate(savedInstanceState);
        Log.i(Config.APP_NAME, "ActProverbDay: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        setContentView(R.layout.act_single_base);
        if (savedInstanceState != null) {
            initializeItem(savedInstanceState);
        }
        mDetector = new GestureDetectorCompat(this, new SwipeGestureListener());


    }

    @Override
    public Proverb getItem(ProverbProvider provider) {
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
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Proverb proverb;
            float dx = event2.getX() - event1.getX();
            if (dx > 0) {
                Log.i(Config.APP_NAME, "get older");
            } else {
                Log.i(Config.APP_NAME, "get newer");
            }
            return true;
        }

    }

}
