package com.veontomo.itaproverb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
        Log.i(Config.APP_NAME, "show single " + Thread.currentThread().getStackTrace()[2].getMethodName());
        return inflater.inflate(R.layout.frag_show_single, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(Config.APP_NAME, "show single " + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(Config.APP_NAME, "show single " + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mIdView = (TextView) getActivity().findViewById(R.id.frag_show_single_id);
        this.mTextView = (TextView) getActivity().findViewById(R.id.frag_show_single_text);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        Log.i(Config.APP_NAME, "show single " + Thread.currentThread().getStackTrace()[2].getMethodName());

    }


    @Override
    public void onStop() {
        Log.i(Config.APP_NAME, "show single " + Thread.currentThread().getStackTrace()[2].getMethodName());
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
        /// TODO: analyze why the very first start of the app on a device produces NullPointerException
        /// if the user clicks on "oracle"
        /// To avoid the problem, the conditionals are added below. But it is better resolve the problem.
        if (this.mIdView != null) {
            this.mIdView.setText(String.valueOf(mProverb.id));
        }
        if (this.mTextView != null) {
            this.mTextView.setText(mProverb.text);
        }

    }
}
