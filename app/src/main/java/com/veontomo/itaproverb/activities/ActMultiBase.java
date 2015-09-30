package com.veontomo.itaproverb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbProvider;
import com.veontomo.itaproverb.api.ProverbSearcher;
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
 * <p/>
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
     * a number that identifies the request to create a new proverb
     */
    private static final int CREATE_PROVERB_REQUEST = 1;
    /**
     * a number that identifies the request to update existing proverb (its status,
     * content or proverb's cancellation)
     */
    private static final int UPDATE_PROVERB_REQUEST = 2;
    /**
     * name of the token under which the first visible proverb of the list view
     * is saved in a bundle
     */
    private static final java.lang.String FIRST_VISIBLE_ITEM_TOKEN = "first_visible";

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

    /**
     * list of proverbs (if fact, this member duplicates info stored in the triple {@link #mIds},
     * {@link #mTexts} and {@link #mStatuses}).
     */
    private List<Proverb> mItems;

    /**
     * performs search in given set
     */
    private ProverbSearcher searcher;


    /**
     * Array of proverb ids that should be displayed out of original proverbs.
     * <br>This filter serves in order to perform search over original proverbs.
     */
    private int[] filter;
    /**
     * Fragment that performs the search
     */
    private FragSearch mSearchFrag;
    /**
     * Index of the first visible item of the list view.
     * It serves to scroll to that item once the activity restarts.
     */
    private int mFirstVisible = -1;

    public abstract List<Proverb> getItems(Storage storage);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!Config.PRODUCTION_MODE) {
            Config.strictModeInit();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_multi_base);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mShowMulti = (FragShowMulti) getSupportFragmentManager().findFragmentById(R.id.act_favorites_frag_show_multi);
        this.mSearchFrag = (FragSearch) getSupportFragmentManager().findFragmentById(R.id.act_favorites_frag_search);
        this.searcher = new ProverbSearcher(this);
    }

    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mIds = savedInstanceState.getIntArray(PROVERB_ID_MULTI_TOKEN);
        mTexts = savedInstanceState.getStringArray(PROVERB_TEXT_MULTI_TOKEN);
        mStatuses = savedInstanceState.getBooleanArray(PROVERB_STATUS_MULTI_TOKEN);
        mFirstVisible = savedInstanceState.getInt(FIRST_VISIBLE_ITEM_TOKEN, -1);
        if (mIds != null && mTexts != null && mStatuses != null) {
            mItems = createMultiProverbs();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mItems != null) {
            this.displayProverbs();
            if (mFirstVisible != -1) {
                this.mShowMulti.scrollTo(mFirstVisible);
            }
        } else {
            // this task launches method getItems in order to fill the activity with content
            ProverbRetrievalTask task = new ProverbRetrievalTask(new Storage(getApplicationContext()), this.mShowMulti, this);
            task.execute();
        }
    }

    /**
     * Passes given list of proverbs to the fragment responsible for displaying them.
     * <p/>
     * <br>In case {@link #filter} is not null, the method filters out proverbs that are not in
     * that list.
     */
    public void displayProverbs() {
        List<Proverb> filtered = new ArrayList();
        if (filter != null) {
            int size = filter.length;
            for (int i = 0; i < size; i++) {
                filtered.add(mItems.get(filter[i]));
            }
        } else {
            filtered = mItems;
        }
        this.mShowMulti.load(filtered);
        this.mShowMulti.updateView();
    }


    /**
     * Sets up {@link #mIds}, {@link #mTexts} and {@link #mStatuses} from given list of proverb.
     *
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
     * Recreates list of proverbs based on split data: array of proverb ids {@link #mIds},
     * array of proverb texts {@link #mTexts} and array of proverb statuses {@link #mStatuses}.
     * It is supposed that the above arrays have the same length.
     *
     * @return list of proverbs
     */
    private List<Proverb> createMultiProverbs() {
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
//        this.mShowMulti = null;
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
        if (mFirstVisible != -1) {
            savedInstanceState.putInt(FIRST_VISIBLE_ITEM_TOKEN, mFirstVisible);
        }

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
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName());
        Intent intent = new Intent(getApplicationContext(), ActEdit.class);
        startActivityForResult(intent, CREATE_PROVERB_REQUEST);
    }

    /**
     * It is called when a user presses the search button
     *
     * @param searchTerm a string that the user wants to look for
     */
    @Override
    public void onSearch(String searchTerm) {
        searcher.find(searchTerm, this.mItems);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), ActShowSingle.class);
        Log.i(Config.APP_NAME, "on item click");
        // index of the proverb in the list of the proverbs to visualize
        // (proverb ids might be in arbitrary order in mIds).
        int index = filter != null ? filter[position] : position;
        intent.putExtra(ActShowSingle.ID_TOKEN, this.mIds[index]);
        intent.putExtra(ActShowSingle.TEXT_TOKEN, this.mTexts[index]);
        intent.putExtra(ActShowSingle.STATUS_TOKEN, this.mStatuses[index]);
        startActivityForResult(intent, UPDATE_PROVERB_REQUEST);
    }

    @Override
    public void onSavePosition(int position) {
        this.mFirstVisible = position;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_PROVERB_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.i(Config.APP_NAME, "creating the proverb");
                String text = data.getStringExtra(ActEdit.TEXT_TOKEN);
                if (text != null) {
                    ProverbProvider provider = new ProverbProvider(new Storage(getApplicationContext()));
                    provider.createProverb(text, data.getBooleanExtra(ActEdit.STATUS_TOKEN, false));
                }
            }
        }
        if (requestCode == UPDATE_PROVERB_REQUEST) {
            if (resultCode == RESULT_OK) {
                int id = data.getIntExtra(ActShowSingle.ID_TOKEN, -1);
                String text = data.getStringExtra(ActShowSingle.TEXT_TOKEN);
                boolean status = data.getBooleanExtra(ActShowSingle.STATUS_TOKEN, false);

            }
        }
    }


    /**
     * {@link #mItems} setter.
     * <p>
     * Once called, it calls method {@link #extractFromMultiProverbs(List)} in order to set values
     * of the triple {@link #mIds}, {@link #mTexts} and {@link #mStatuses} that are duplicates
     * of {@link #mItems}.</p>
     *
     * @param proverbs
     */
    public void setItems(List<Proverb> proverbs) {
        this.mItems = proverbs;
        extractFromMultiProverbs(proverbs);
    }

    /**
     * {@link #filter} setter.
     *
     * @param filter
     */
    public void setFilter(int[] filter) {
        this.filter = filter;
    }

    /**
     * Disables search input field
     */
    public void onEmptyInput() {
        if (this.mSearchFrag != null) {
            this.mSearchFrag.disableSearch();
        }
    }
}
