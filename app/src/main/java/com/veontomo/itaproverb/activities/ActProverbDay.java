package com.veontomo.itaproverb.activities;

import android.os.Bundle;
import android.util.Log;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbProvider;

/**
 * Displays a proverb of the day along with manager panel.
 */
public class ActProverbDay extends ActSingleBase {
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

    }

    @Override
    public Proverb getItem(ProverbProvider provider){
        return provider.todayProverb();
    }
}
