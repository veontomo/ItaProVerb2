package com.veontomo.itaproverb.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;

/**
 * Created by Mario Rossi on 23/09/2015 at 12:25.
 *
 * @author veontomo@gmail.com
 * @since xx.xx
 */
public class FragShowSingleProverbDay extends FragShowSingle {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(Config.APP_NAME, "show single " + Thread.currentThread().getStackTrace()[2].getMethodName());
        return inflater.inflate(R.layout.frag_show_single_proverb_day, container, false);
    }
}
