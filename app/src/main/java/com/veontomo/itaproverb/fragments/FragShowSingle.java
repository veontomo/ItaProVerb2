package com.veontomo.itaproverb.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Proverb;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragShowSingle extends Fragment {

    public FragShowSingle() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_show_single, container, false);
    }


    public void load(Proverb p) {
        ((TextView) getActivity().findViewById(R.id.frag_show_single_id)).setText(String.valueOf(p.id));
        ((TextView) getActivity().findViewById(R.id.frag_show_single_text)).setText(p.text);

    }
}
