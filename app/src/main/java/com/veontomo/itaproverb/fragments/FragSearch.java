package com.veontomo.itaproverb.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.veontomo.itaproverb.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragSearch extends Fragment {

    /**
     * edit text view that hold the user input
     */
    private EditText mInputField;

    /**
     * the button that confirms user input
     */
    private ImageButton mButton;

    /**
     * Hosting activity that is cast to {@link com.veontomo.itaproverb.fragments.FragSearch.FragSearchActions}
     *
     */
    private FragSearchActions hostActivity;

    public FragSearch() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_search, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mInputField = (EditText) getActivity().findViewById(R.id.frag_search_input);
        this.mButton = (ImageButton) getActivity().findViewById(R.id.frag_search_button);
        this.hostActivity = (FragSearchActions) getActivity();
        attachListeners();
    }

    /**
     * Attaches a listener to the search button
     */
    private void attachListeners() {
        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostActivity.onSearch(mInputField.getEditableText().toString());
            }
        });

    }

    @Override
    public void onStop() {
        this.mButton = null;
        this.mInputField = null;
        super.onStop();
    }

    /**
     * interface that hosting activity should implement in order to receive actions from this fragment
     */
    public interface FragSearchActions {
        /**
         * It is called when a user presses the search button
         * @param searchTerm a string that the user wants to look for
         */
        void onSearch(String searchTerm);
    }
}
