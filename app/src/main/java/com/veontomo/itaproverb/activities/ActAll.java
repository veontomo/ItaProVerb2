package com.veontomo.itaproverb.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.AppInit;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbProvider;
import com.veontomo.itaproverb.api.Storage;
import com.veontomo.itaproverb.fragments.FragAddProverb;
import com.veontomo.itaproverb.fragments.FragSearch;
import com.veontomo.itaproverb.fragments.FragShowMulti;

import java.util.List;

public class ActAll extends AppCompatActivity implements FragAddProverb.FragAddActions,
        FragSearch.FragSearchActions, FragShowMulti.ShowMultiActions {

    /**
     * a fragment that takes care of visualization of multiple proverbs
     */
    private FragShowMulti mShowMulti;

    /**
     * Proverbs that are passed to the fragment {@link #mShowMulti}.
     */
    private List<Proverb> mProverbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!Config.PRODUCTION_MODE) {
            Config.strictModeInit();
        }
        super.onCreate(savedInstanceState);
        Log.i(Config.APP_NAME, "activity show all: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        setContentView(R.layout.act_favorites);
        AppInit.loadProverbs(getApplicationContext(), Config.PROVERB_SRC, Config.ENCODING);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(Config.APP_NAME, "activity show all: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mShowMulti = (FragShowMulti) getSupportFragmentManager().findFragmentById(R.id.act_favorites_frag_show_multi);
        ProverbProvider provider = new ProverbProvider(new Storage(getApplicationContext()));
        this.mProverbs = provider.getAllProverbs();
        this.mShowMulti.load(mProverbs);
        this.mShowMulti.updateView();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(Config.APP_NAME, "activity show all: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        getSupportFragmentManager().putFragment(outState, "mContent", this.mShowMulti);
    }

    @Override
    public void onStop() {
        Log.i(Config.APP_NAME, "activity show all: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mShowMulti = null;
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
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
     * It is called when a user wants to add a new item
     */
    @Override
    public void onAddNew() {
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented");
    }

    /**
     * It is called when a user presses the search button
     *
     * @param searchTerm a string that the user wants to look for
     */
    @Override
    public void onSearch(String searchTerm) {
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented");
    }

    @Override
    public void onItemClick(int position) {
        /// TODO
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented");
        Log.i(Config.APP_NAME, "click on " + position);
        Log.i(Config.APP_NAME, this.mProverbs.get(position).text);
    }
}