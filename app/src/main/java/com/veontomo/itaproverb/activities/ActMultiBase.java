package com.veontomo.itaproverb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.AppInit;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.Storage;
import com.veontomo.itaproverb.fragments.FragAddProverb;
import com.veontomo.itaproverb.fragments.FragSearch;
import com.veontomo.itaproverb.fragments.FragShowMulti;
import com.veontomo.itaproverb.tasks.ProverbRetrievalTask;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract activity deals with multiple proverbs: displays them,
 * performs search among them.
 *
 * Activities that extend this one must implement method {@link #getItems(Storage)} that determines
 * the content.
 */
public abstract class ActMultiBase extends AppCompatActivity implements FragAddProverb.FragAddActions,
        FragSearch.FragSearchActions, FragShowMulti.ShowMultiActions {

    /**
     * name of the token under which all proverb texts are saved in a bundle
     * as an array of strings
     */
    private static final String PROVERB_TEXT_MULTI_TOKEN = "text";
    /**
     * name of the token under which the proverb ids are saved in a bundle
     * as an array of integers
     */
    private static final String PROVERB_ID_MULTI_TOKEN = "id";
    /**
     * name of the token under which the proverb statuses (i.e. being favorite or not)
     * are saved in a bundle as an array of booleans.
     */
    private static final String PROVERB_STATUS_MULTI_TOKEN = "status";

    /**
     * a fragment that takes care of visualization of multiple proverbs
     */
    private FragShowMulti mShowMulti;
    /**
     * list of proverb ids that this activity should visualize
     */
    private int[] mIds;
    /**
     * list of proverb texts that this activity should visualize
     */
    private String[] mTexts;
    /**
     * list of proverb statuses that this activity should visualize
     */
    private boolean[] mStatuses;

    public abstract List<Proverb> getItems(Storage storage);


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
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mIds = savedInstanceState.getIntArray(PROVERB_ID_MULTI_TOKEN);
        mTexts = savedInstanceState.getStringArray(PROVERB_TEXT_MULTI_TOKEN);
        mStatuses = savedInstanceState.getBooleanArray(PROVERB_STATUS_MULTI_TOKEN);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mIds != null && mTexts != null && mStatuses != null) {
            List<Proverb> proverbs = createMultiProverbs(mIds, mTexts, mStatuses);
            this.mShowMulti.load(proverbs);
            this.mShowMulti.updateView();
        } else {
            ProverbRetrievalTask task = new ProverbRetrievalTask(new Storage(getApplicationContext()), this.mShowMulti, this);
            task.execute();
        }
    }


    /**
     * Sets up {@link #mIds}, {@link #mTexts} and {@link #mStatuses} from given list of proverb.
     * @param proverbs
     */
    public void extractFromMultiProverbs(@NonNull List<Proverb> proverbs) {
        int size = proverbs.size();
        if (size > 0) {
            mIds = new int[size];
            mTexts = new String[size];
            mStatuses = new boolean[size];
            Proverb proverb;
            for (int i = 0; i < size; i++) {
                proverb = proverbs.get(i);
                mIds[i] = proverb.id;
                mTexts[i] = proverb.text;
                mStatuses[i] = proverb.isFavorite;
            }
        }
    }


    /**
     * Recreates list of proverbs based on split data: array of proverb ids, array of proverb texts,
     * array of proverb statuses.
     * It is supposed that the input arrays have the same length.
     * @param ids
     * @param texts
     * @param statuses
     * @return list of proverbs
     */
    private List<Proverb> createMultiProverbs(@NonNull int[] ids, @NonNull String[] texts, @NonNull boolean[] statuses) {
        List<Proverb> proverbs = new ArrayList<>();
        int size = mIds.length;
        Proverb proverb;
        for (int i = 0; i < size; i++) {
            proverb = new Proverb(mIds[i], mTexts[i], mStatuses[i]);
            proverbs.add(proverb);
        }
        return proverbs;
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putIntArray(PROVERB_ID_MULTI_TOKEN, mIds);
        savedInstanceState.putStringArray(PROVERB_TEXT_MULTI_TOKEN, mTexts);
        savedInstanceState.putBooleanArray(PROVERB_STATUS_MULTI_TOKEN, mStatuses);

        super.onSaveInstanceState(savedInstanceState);
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
        Intent intent = new Intent(getApplicationContext(), ActShowSingle.class);
        startActivity(intent);
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName() + " not implemented");
    }
}
