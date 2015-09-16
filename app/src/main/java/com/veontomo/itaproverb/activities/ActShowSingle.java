package com.veontomo.itaproverb.activities;

import android.content.Intent;
import android.os.Bundle;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbProvider;

/**
 * Displays single proverb along with manager panel
 */
public class ActShowSingle extends ActSingleBase {
    /**
     * name of the token under which the proverb id is stored in the bundle
     */
    public static final String ID_TOKEN = "id";
    /**
     * name of the token under which the proverb text is stored in the bundle
     */
    public static final String TEXT_TOKEN = "text";
    /**
     * name of the token under which the proverb status is stored in the bundle
     */
    public static final String STATUS_TOKEN = "status";
    /**
     * Id of the proverb for which this activity has been called
     */
    private int mId;
    /**
     * Text of the proverb for which this activity has been called
     */
    private String mText;
    /**
     * Status of the proverb for which this activity has been called
     */
    private boolean mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_single_base);
        Bundle b = savedInstanceState;
        if (b == null) {
            Intent intent = getIntent();
            if (intent != null) {
                b = getIntent().getExtras();
            }
        }
        if (b != null) {
            this.mId = b.getInt(ID_TOKEN, -1);
            this.mText = b.getString(TEXT_TOKEN);
            this.mStatus = b.getBoolean(STATUS_TOKEN, false);
        }
    }


    @Override
    public Proverb getItem(ProverbProvider provider) {
        return new Proverb(mId, mText, mStatus);
    }

}
