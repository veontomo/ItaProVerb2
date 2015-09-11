package com.veontomo.itaproverb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;

/**
 * A fragment that displays a single proverb.
 */
public class FragShowSingle extends Fragment {

    /**
     * proverb that this fragment should load
     */
    private Proverb mProverb;

    /**
     * Text view of the fragment layout that displays proverb id
     */
    private TextView mIdView;

    /**
     * Text view of the fragment layout that displays proverb text
     */
    private TextView mTextView;

    public FragShowSingle() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_show_single, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        this.mIdView = (TextView) getActivity().findViewById(R.id.frag_show_single_id);
        this.mTextView = (TextView) getActivity().findViewById(R.id.frag_show_single_text);
    }


    @Override
    public void onStop() {
        this.mTextView = null;
        this.mIdView = null;
        super.onStop();
    }

    /**
     * Loads data from given proverb into corresponding layout elements
     *
     * @param proverb proverb to be displayed
     */
    public void load(Proverb proverb) {
        this.mProverb = proverb;
    }

    /**
     * Make the fragment layout visualize {@link #mProverb}.
     */
    public void updateView() {
        this.mIdView.setText(String.valueOf(mProverb.id));
        this.mTextView.setText(mProverb.text);

    }
}
