package com.veontomo.itaproverb.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.veontomo.itaproverb.R;

/**
 * A fragment that collects a user intentions to do the following:
 * <br/> 1. change the proverb status (favorite/non-favorite)
 * <br/> 2. edit the proverb
 * <br/> 3. share the proverb
 * <br/> 4. remove the proverb
 */
public class FragManagerPanel extends Fragment {

    /**
     * An image view that holds "share" image
     */
    private ImageView mShare;
    /**
     * An image view that holds "edit" button
     */
    private ImageView mEdit;

    /**
     * An image view that holds "cancel" button
     */
    private ImageView mCancel;

    /**
     * An image view that holds "cancel" button
     */
    private ImageView mStatus;

    /**
     * Hosting activity that is cast to {@link com.veontomo.itaproverb.fragments.FragManagerPanel.ManagerPanelActions}
     *
     */
    private ManagerPanelActions hostActivity;

    public FragManagerPanel() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_manager, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mShare = (ImageView) getActivity().findViewById(R.id.frag_manager_share);
        this.mEdit = (ImageView) getActivity().findViewById(R.id.frag_manager_edit);
        this.mCancel = (ImageView) getActivity().findViewById(R.id.frag_manager_cancel);
        this.mStatus = (ImageView) getActivity().findViewById(R.id.frag_manager_star);

        this.hostActivity = (ManagerPanelActions) getActivity();
    }


    /**
     * interface that hosting activity should implement in order to receive actions from this fragment
     */
    public interface ManagerPanelActions {
        /**
         * It is called when a user wants to change the proverb status (change favorite one into a normal
         * or vice versa)
         */
        void onStatusChange();

        /**
         * It is called when a user wants to edit the proverb
         */
        void onEdit();

        /**
         * It is called when a user wants to share the proverb
         */
        void onShare();

        /**
         * It is called when a user wants to delete the proverb
         */
        void onDelete();
    }
}
