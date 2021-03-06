package com.veontomo.itaproverb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment to display many proverbs
 */
public class FragShowMulti extends Fragment {
    /**
     * list of proverbs that this fragment should display
     */
    private List<Proverb> mProverbs;

    /**
     * adapter that is responsible for displaying list of proverbs
     */
    private ProverbAdapter mAdapter;

    /**
     * a list view that accommodates the list of proverbs
     */
    private ListView mListView;

    /**
     * Activity that hosts this fragment.
     * <p>The activity is cast to {@link FragShowMulti.ShowMultiActions} interface.</p>
     */
    private ShowMultiActions hostActivity;

    /**
     * Position of ad inside the list of proverbs
     */
    private int adPos = -1;

    public FragShowMulti() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_show_multi, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.hostActivity = (ShowMultiActions) getActivity();
        this.mAdapter = new ProverbAdapter(getContext(), new ArrayList<Proverb>());
        this.mAdapter.setAdPosition(this.adPos);
        this.mListView = (ListView) getActivity().findViewById(R.id.frag_show_multi_list);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = mAdapter.getItemIndex(position, -1);
                if (index != -1) {
                    hostActivity.onItemClick(index);
                }
            }
        });

    }

    public void load(List<Proverb> proverbs) {
        this.mProverbs = proverbs;
        this.mAdapter.load(this.mProverbs);
    }

    public void updateView() {
        this.mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onPause() {
        if (hostActivity != null && mListView != null) {
            hostActivity.onSavePosition(mListView.getFirstVisiblePosition());

        }
        super.onPause();
    }

    /**
     * Scrolls to the given position
     *
     * @param position
     */
    public void scrollTo(int position) {
        if (this.mListView != null) {
            this.mListView.setSelection(position);
        }
    }

    public void setAdPos(int adPos) {
        this.adPos = adPos;
        if (this.mAdapter != null) {
            this.mAdapter.setAdPosition(adPos);
        }

    }

    public interface ShowMultiActions {
        /**
         * It is called when an item that this fragment visualizes gets clicked.
         *
         * @param position
         */
        void onItemClick(int position);

        /**
         * it is called to save the index of the first proverb currently visualized
         *
         * @param position
         */
        void onSavePosition(int position);
    }
}
