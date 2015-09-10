package com.veontomo.itaproverb.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veontomo.itaproverb.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragManager extends Fragment {

    public FragManager() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_manager, container, false);
    }
}
