package com.veontomo.itaproverb.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.ProverbDay;
import com.veontomo.itaproverb.fragments.FragManagerPanel;
import com.veontomo.itaproverb.fragments.FragShowSingle;

public class ActProverbOracle extends AppCompatActivity implements FragManagerPanel.ManagerPanelActions {

    /**
     * fragment that takes care of visialization of the proverb
     */
    private FragShowSingle mFragShowSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_proverb_oracle);

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.mFragShowSingle = (FragShowSingle) getSupportFragmentManager().findFragmentById(R.id.act_proverb_oracle_frag_proverb);
        ProverbDay provider = new ProverbDay();
        this.mFragShowSingle.load(provider.randomProverb());
        this.mFragShowSingle.updateView();
    }


    @Override
    protected void onStop() {
        this.mFragShowSingle = null;
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
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented") ;
    }

    /**
     * It is called when a user wants to edit the proverb
     */
    @Override
    public void onEdit() {
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented") ;
    }

    /**
     * It is called when a user wants to share the proverb
     */
    @Override
    public void onShare() {
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented") ;
    }

    /**
     * It is called when a user wants to delete the proverb
     */
    @Override
    public void onDelete() {
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented") ;
    }
}
