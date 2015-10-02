package com.veontomo.itaproverb.activities;

import android.os.Bundle;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Logger;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbProvider;

/**
 * Displays an oracle proverb along with manager panel.
 */
public class ActOracle extends ActSingleBase {

    /**
     * title with which the oracle proverb is shared on a social network
     */
    @Override
    public String getSharePostTitle() {
        return getString(R.string.share_post_title_oracle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i("ActOracle: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_single_base);
    }

    @Override
    public Proverb getItem(ProverbProvider provider){
        return provider.randomProverb();
    }
}
