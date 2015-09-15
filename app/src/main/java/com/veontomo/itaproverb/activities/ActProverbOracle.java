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

public class ActProverbOracle extends AppCompatActivity implements FragManagerPanel.ManagerPanelActions {
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
    private FragShowSingle mFragShowSingle;

    /**
     * fragment that visualizes the manager panel
     */
    private FragManagerPanel mFragManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!Config.PRODUCTION_MODE) {
            Config.strictModeInit();
        }
        super.onCreate(savedInstanceState);
        Log.i(Config.APP_NAME, "activity oracle: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        setContentView(R.layout.act_proverb_oracle);
        if (savedInstanceState != null) {
            this.mProverb = new Proverb(savedInstanceState.getInt(PROVERB_ID_TOKEN),
                    savedInstanceState.getString(PROVERB_TEXT_TOKEN),
                    savedInstanceState.getBoolean(PROVERB_STATUS_TOKEN));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Config.APP_NAME, "activity oracle: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mFragShowSingle = (FragShowSingle) getSupportFragmentManager().findFragmentById(R.id.act_proverb_oracle_frag_proverb);
        this.mFragManager = (FragManagerPanel) getSupportFragmentManager().findFragmentById(R.id.act_proverb_oracle_frag_manager_panel);
        provider = new ProverbProvider(new Storage(getApplicationContext()));
        if (this.mProverb == null) {
            this.mProverb = provider.randomProverb();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mFragShowSingle.load(this.mProverb);
        this.mFragShowSingle.updateView();

        this.mFragManager.setFavorite(this.mProverb.isFavorite);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(Config.APP_NAME, "activity oracle: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        outState.putString(PROVERB_TEXT_TOKEN, this.mProverb.text);
        outState.putInt(PROVERB_ID_TOKEN, this.mProverb.id);
        outState.putBoolean(PROVERB_STATUS_TOKEN, this.mProverb.isFavorite);
    }

    @Override
    public void onPause(){
        if (this.shouldChangeStatus){
            provider.setProverbStatus(this.mProverb.id, !this.mProverb.isFavorite);
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        this.mFragShowSingle = null;
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
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented");
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
