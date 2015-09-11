package com.veontomo.itaproverb.activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbDay;
import com.veontomo.itaproverb.fragments.FragManagerPanel;
import com.veontomo.itaproverb.fragments.FragShowSingle;

public class ActProverbDay extends AppCompatActivity implements FragManagerPanel.ManagerPanelActions, FragShowSingle.ShowSingleActions {

    /**
     * Fragments that manages single proverb visualisation.
     */
    private FragShowSingle mProverbFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!Config.PRODUCTION_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
//                    .detectNetwork()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proverb_day);
    }

    @Override
    public void onStart(){
        super.onStart();
        ProverbDay provider = new ProverbDay();
        Proverb proverb = provider.todayProverb();
        mProverbFragment = (FragShowSingle) getSupportFragmentManager().findFragmentById(R.id.act_proverb_day_frag_proverb);
        mProverbFragment.load(proverb);
        mProverbFragment.updateView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proverb_day, menu);
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

    @Override
    public void onStatusChange() {
        /// TODO
        Log.i(Config.APP_NAME, "method onStatusChanged has yet to be implemented.");
    }

    @Override
    public void onEdit() {
        /// TODO
        Log.i(Config.APP_NAME, "method onEdit has yet to be implemented.");

    }

    @Override
    public void onShare() {
        /// TODO
        Log.i(Config.APP_NAME, "method onShare has yet to be implemented.");

    }

    @Override
    public void onDelete() {
        /// TODO
        Log.i(Config.APP_NAME, "method onDelete has yet to be implemented.");

    }

    @Override
    public void onNext() {
        /// TODO
        Log.i(Config.APP_NAME, "method onNext in ActProverb is not implemented yet");
    }

    @Override
    public void onPrevious() {
        /// TODO
        Log.i(Config.APP_NAME, "method onPrevious in ActProverb is not implemented yet");
    }
}
