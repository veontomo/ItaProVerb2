package com.veontomo.itaproverb.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;

import java.util.List;

/**
 * A fragment to display many proverbs
 */
public class FragShowMulti extends Fragment {
    private List<Proverb> mProverbs;

    public FragShowMulti() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_show_multi, container, false);
    }

    public void load(List<Proverb> proverbs) {
        this.mProverbs = proverbs;
    }

    public void updateView(){
        /// TODO
        Log.i(Config.APP_NAME, "updating view: " + this.mProverbs.size());
    }
}
