package com.veontomo.itaproverb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbProvider;
import com.veontomo.itaproverb.api.Storage;
import com.veontomo.itaproverb.fragments.FragManagerPanel;
import com.veontomo.itaproverb.fragments.FragShowSingle;

/**
 * Displays a single proverb along with the manager panel.
 */
public abstract class ActSingleBase extends AppCompatActivity implements FragManagerPanel.ManagerPanelActions {
    /**
     * name of the token under which the proverb text is saved in a bundle
     */
    private static final String PROVERB_TEXT_TOKEN = "text";
    /**
     * name of the token under which the proverb id is saved in a bundle
     */
    private static final String PROVERB_ID_TOKEN = "id";
    /**
     * name of the token under which the proverb status (i.e. being favorite or not)
     * is saved in a bundle
     */
    private static final String PROVERB_STATUS_TOKEN = "status";
    /**
     * a number that identifies the request to delete the proverb
     */
    private static final int DELETE_REQUEST = 1;

    /**
     * a number that identifies the request to edit the proverb
     */
    private static final int EDIT_REQUEST = 2;

    /**
     * fragment that takes care of visualization of the proverb
     */
    protected FragShowSingle mFragItem;

    /**
     * fragment that visualizes the manager panel
     */
    protected FragManagerPanel mFragManager;

    /**
     * Proverb that this activity should visualize
     */
    private Proverb mProverb;


    /**
     * Whether the proverb status should be changed.
     * <p/>
     * The proverb status is not changed immediately, but the request
     * to change the status is registered and is taken into account
     * when the activity is about to be over.
     */
    private boolean shouldChangeStatus = false;

    /**
     * performs operations with proverbs
     */
    private ProverbProvider provider;

    public void initializeItem(Bundle savedInstanceState) {
        this.mProverb = new Proverb(savedInstanceState.getInt(PROVERB_ID_TOKEN),
                savedInstanceState.getString(PROVERB_TEXT_TOKEN),
                savedInstanceState.getBoolean(PROVERB_STATUS_TOKEN));

    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Config.APP_NAME, "single base activity: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mFragItem = (FragShowSingle) getSupportFragmentManager().findFragmentById(R.id.act_single_base_frag_proverb);
        this.mFragManager = (FragManagerPanel) getSupportFragmentManager().findFragmentById(R.id.act_single_base_frag_manager_panel);
        provider = new ProverbProvider(new Storage(getApplicationContext()));

        if (this.mProverb == null) {
            this.mProverb = getItem(provider);
        }
    }

    public abstract Proverb getItem(ProverbProvider provider);

    @Override
    public void onResume() {
        super.onResume();
        this.mFragItem.load(this.mProverb);
        this.mFragItem.updateView();
        this.mFragManager.setFavorite(this.mProverb.isFavorite);
        registerListeners();
    }

    /**
     * Register listeners.
     * <p>It is supposed to be overridden by a subclass in order to have non-trivial behaviour.
     * It is called in {@link #onResume()} method.</p>
     */
    protected void registerListeners(){};

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(Config.APP_NAME, "single base activity: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        outState.putString(PROVERB_TEXT_TOKEN, this.mProverb.text);
        outState.putInt(PROVERB_ID_TOKEN, this.mProverb.id);
        outState.putBoolean(PROVERB_STATUS_TOKEN, this.mProverb.isFavorite);
    }

    @Override
    public void onPause() {
        if (this.shouldChangeStatus) {
            provider.setProverbStatus(this.mProverb.id, !this.mProverb.isFavorite);
        }
        unregisterListeners();
        super.onPause();
    }

    /**
     * Unset previously registered listeners.
     * <p>It is called in {@link #onPause()} method.</p>
     */
    protected void unregisterListeners() {}

    @Override
    protected void onStop() {
        this.mFragItem = null;
        this.provider = null;
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proverb_oracle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * It is called when a user wants to change the proverb status (change favorite one into a normal
     * or vice versa)
     */
    @Override
    public void onStatusChange() {
        this.shouldChangeStatus = !this.shouldChangeStatus;
        this.mFragManager.setFavorite(this.shouldChangeStatus);
    }

    /**
     * It is called when a user wants to edit the proverb
     */
    @Override
    public void onEdit() {
        Log.i(Config.APP_NAME, "oracle: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        Intent intent = new Intent(getApplicationContext(), ActEdit.class);
        intent.putExtra(ActDelete.TEXT_TOKEN, mProverb.text);
        startActivityForResult(intent, EDIT_REQUEST);
    }

    /**
     * It is called when a user wants to share the proverb
     */
    @Override
    public void onShare() {
        Intent intent = new Intent(getApplicationContext(), ActShare.class);
        intent.putExtra(ActShare.TEXT_TOKEN, this.mProverb.text);
        startActivity(intent);
    }

    /**
     * It is called when a user wants to delete the proverb
     */
    @Override
    public void onDelete() {
        Log.i(Config.APP_NAME, "oracle: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        Intent intent = new Intent(getApplicationContext(), ActDelete.class);
        intent.putExtra(ActDelete.ID_TOKEN, mProverb.id);
        intent.putExtra(ActDelete.TEXT_TOKEN, mProverb.text);
        intent.putExtra(ActDelete.STATUS_TOKEN, mProverb.isFavorite);
        startActivityForResult(intent, DELETE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DELETE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.i(Config.APP_NAME, "deleting the proverb");
                ProverbProvider provider = new ProverbProvider(new Storage(getApplicationContext()));
                provider.deleteProverb(mProverb.id);
                finish();
            }
            return;
        }
        if (requestCode == EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.i(Config.APP_NAME, "updating the proverb");
                ProverbProvider provider = new ProverbProvider(new Storage(getApplicationContext()));
                provider.updateProverb(mProverb.id, data.getStringExtra(ActEdit.TEXT_TOKEN));
                finish();
            }
            return;

        }
    }
}