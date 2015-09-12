package com.veontomo.itaproverb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.veontomo.itaproverb.R;

/**
 * A fragment that collects a user intentions to add a new proverb
 */
public class FragAddProverb extends Fragment {

    /**
     * an image clicks on which mean that the user wants to add a new item (proverb)
     */
    private ImageView mAdd;

    /**
     * Hosting activity that is cast to {@link FragAddProverb.FragAddActions}.
     */
    private FragAddActions hostActivity;

    public FragAddProverb() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_add_proverb, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mAdd = (ImageView) getActivity().findViewById(R.id.frag_add_proverb_add_button);

        this.hostActivity = (FragAddActions) getActivity();
        this.mAdd.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                if (hostActivity != null){
                    hostActivity.onAddNew();
                }
            }
        });
    }


    @Override
    public void onStop() {
        this.mAdd.setOnClickListener(null);
        this.hostActivity = null;
        this.mAdd = null;
        super.onStop();
    }

    public interface FragAddActions {
        /**
         * It is called when a user wants to add a new item
         */
        void onAddNew();
    }

}
