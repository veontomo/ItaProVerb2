package com.veontomo.itaproverb.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.AppInit;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbProvider;
import com.veontomo.itaproverb.api.Storage;
import com.veontomo.itaproverb.fragments.FragAddProverb;
import com.veontomo.itaproverb.fragments.FragSearch;
import com.veontomo.itaproverb.fragments.FragShowMulti;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ActFavorites extends AppCompatActivity implements FragAddProverb.FragAddActions, FragSearch.FragSearchActions {

    /**
     * a fragment that takes care of visualization of multiple proverbs
     */
    private FragShowMulti mShowMulti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!Config.PRODUCTION_MODE) {
            Config.strictModeInit();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_favorites);
        AppInit.loadProverbs(getApplicationContext(), Config.PROVERB_SRC, Config.ENCODING);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mShowMulti = (FragShowMulti) getSupportFragmentManager().findFragmentById(R.id.act_favorites_frag_show_multi);
        ProverbProvider provider = new ProverbProvider();
        List<Proverb> proverbs = provider.favoriteProverbs();
        this.mShowMulti.load(proverbs);
        this.mShowMulti.updateView();
    }


    @Override
    public void onStop() {
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
    public void onCreate() {
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
}
