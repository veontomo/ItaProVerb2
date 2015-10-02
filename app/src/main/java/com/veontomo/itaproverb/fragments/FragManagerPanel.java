package com.veontomo.itaproverb.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private ImageView mDelete;

    /**
     * An image view that holds "cancel" button
     */
    private ImageView mStatus;

    /**
     * Hosting activity that is cast to {@link com.veontomo.itaproverb.fragments.FragManagerPanel.ManagerPanelActions}
     */
    private ManagerPanelActions hostActivity;

    /**
     * opacity of the icon corresponding to a favorite proverb
     */
    private final static float FAVORITE = 1f;

    /**
     * opacity of the icon corresponding to a non-favorite proverb
     */
    private final static float NON_FAVORITE = 0.2f;

    public FragManagerPanel() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_manager_panel, container, false);
    }



    @Override
    public void onStart() {
        super.onStart();
        this.mShare = (ImageView) getActivity().findViewById(R.id.frag_manager_share);
        this.mEdit = (ImageView) getActivity().findViewById(R.id.frag_manager_edit);
        this.mDelete = (ImageView) getActivity().findViewById(R.id.frag_manager_delete);
        this.mStatus = (ImageView) getActivity().findViewById(R.id.frag_manager_star);

        this.hostActivity = (ManagerPanelActions) getActivity();

        attachListeners();

    }


    @Override
    public void onStop() {
        detachListeners();
        this.hostActivity = null;
        this.mStatus = null;
        this.mDelete = null;
        this.mEdit = null;
        this.mShare = null;
        super.onStop();
    }


    /**
     * Detaches listeners from the buttons in the opposite order as it was done in {@link #attachListeners()}.
     *
     */
    private void detachListeners() {
        this.mDelete.setOnClickListener(null);
        this.mEdit.setOnClickListener(null);
        this.mStatus.setOnClickListener(null);
        this.mShare.setOnClickListener(null);

    }


    /**
     * Attaches listeners to the buttons.
     *
     * One could create a method for these similar-looking actions, but this approach would
     * require too much additional code (i.e., try-catch block and Reflection library), so
     * I have decided to set up the all the listeners manually.
     *
     */
    private void attachListeners() {
        this.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostActivity.onShare();
            }
        });
        this.mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostActivity.onStatusChange();
            }
        });
        this.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostActivity.onEdit();
            }
        });
        this.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostActivity.onDelete();
            }
        });
    }


    /**
     * Sets the opacity of the star.
     * @param status
     */
    public void setFavorite(boolean status){
        this.mStatus.setAlpha(status ? FAVORITE : NON_FAVORITE);
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
