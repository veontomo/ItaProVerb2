package com.veontomo.itaproverb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;

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
    private ImageView mButton;

    /**
     * Hosting activity that is cast to {@link com.veontomo.itaproverb.fragments.FragSearch.FragSearchActions}
     */
    private FragSearchActions hostActivity;
    /**
     * a watcher that detects changes of {@link #mInputField} input field.
     */
    private TextWatcher mWatcher;

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
        this.mButton = (ImageView) getActivity().findViewById(R.id.frag_search_button);
        this.hostActivity = (FragSearchActions) getActivity();
        this.mWatcher = new InputFieldChangeWatcher(this.hostActivity);
        attachListeners();
    }

    /**
     * Attaches listeners to the input field and search button
     */
    private void attachListeners() {
        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostActivity.onSearch(mInputField.getEditableText().toString());
            }
        });
        this.mInputField.addTextChangedListener(this.mWatcher);

    }

    @Override
    public void onStop() {
        detachListeners();
        this.mButton = null;
        this.mInputField = null;
        super.onStop();
    }

    private void detachListeners() {
        this.mInputField.removeTextChangedListener(this.mWatcher);
        this.mButton.setOnClickListener(null);
    }


    /**
     * interface that hosting activity should implement in order to receive actions from this fragment
     */
    public interface FragSearchActions {
        /**
         * It is called when a user presses the search button
         *
         * @param searchTerm a string that the user wants to look for
         */
        void onSearch(String searchTerm);
    }

    private final class InputFieldChangeWatcher implements TextWatcher {
        /**
         * an instance of a class that is able to process the calls from this
         * watcher
         */
        private final FragSearchActions handler;

        public InputFieldChangeWatcher(final FragSearchActions handler) {
            this.handler = handler;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (handler != null) {
                handler.onSearch(s.toString());
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
        }
    }

    ;
}
