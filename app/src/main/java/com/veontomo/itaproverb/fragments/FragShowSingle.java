package com.veontomo.itaproverb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    /**
     * proverb that this fragment should load
     */
    private Proverb mItem;

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

        this.mIdView = (TextView) getActivity().findViewById(R.id.frag_show_single_id);
        this.mTextView = (TextView) getActivity().findViewById(R.id.frag_show_single_text);
    }


    /**
     * Loads data from given proverb into corresponding layout elements
     *
     * @param p proverb to be displayed
     */
    public void load(Proverb p) {
        this.mItem = p;
    }

    /**
     * Make the fragment layout visualize {@link #mItem}.
     */
    public void updateView() {
        this.mIdView.setText(String.valueOf(mItem.id));
        this.mTextView.setText(mItem.text);

    }
}
