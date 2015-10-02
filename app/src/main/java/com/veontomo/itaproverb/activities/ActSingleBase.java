package com.veontomo.itaproverb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Logger;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.ProverbProvider;
import com.veontomo.itaproverb.api.Storage;
import com.veontomo.itaproverb.fragments.FragManagerPanel;
import com.veontomo.itaproverb.fragments.FragShowSingle;
import com.veontomo.itaproverb.fragments.NotificationTimeFragment;

/**
 * Displays a single proverb along with the manager panel.
 */
public abstract class ActSingleBase extends AppCompatActivity implements FragManagerPanel.ManagerPanelActions {
    /**
     * name of the token under which the proverb text is saved in a bundle
     */
    public static final String TEXT_TOKEN = "text";
    /**
     * name of the token under which the proverb id is saved in a bundle
     */
    public static final String ID_TOKEN = "id";
    /**
     * name of the token under which the proverb status (i.e. being favorite or not)
     * is saved in a bundle
     */
    public static final String STATUS_TOKEN = "status";
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
    // TODO: to delete
    private String marker = "ActSingleBase: ";
    /**
     * a view that displays the ad
     */
    private AdView mAdView;

    /**
     * title with which the post is shared on a social network
     */
    public String getSharePostTitle() {
        return getString(R.string.share_post_title);
    }

    public ProverbProvider getProverbProvider() {
        return provider;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
//        if (!Config.PRODUCTION_MODE) {
//            Config.strictModeInit();
//        }

        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            initializeItem(savedInstanceState);
        }
    }

    public void initializeItem(Bundle b) {
        this.mProverb = new Proverb(b.getInt(ID_TOKEN),
                b.getString(TEXT_TOKEN),
                b.getBoolean(STATUS_TOKEN));

    }


    @Override
    protected void onStart() {
        super.onStart();
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mFragItem = (FragShowSingle) getSupportFragmentManager().findFragmentById(R.id.act_single_base_frag_proverb);
        this.mFragManager = (FragManagerPanel) getSupportFragmentManager().findFragmentById(R.id.act_single_base_frag_manager_panel);
        this.provider = new ProverbProvider(new Storage(getApplicationContext()));

        if (this.mProverb == null) {
            this.mProverb = getItem(provider);
        }

        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(Config.AD_UNIT_ID);
        if (mAdView.getAdSize() != null || mAdView.getAdUnitId() != null) {
            AdRequest.Builder builder = new AdRequest.Builder();
            builder.addTestDevice("7AF0B9ACA88543F6856087558ACFE7DE");
            builder.addTestDevice("8481E761F3F746FD40AA4D04F0D60CA7");
            builder.addTestDevice("5970A479C5E4B2AD245BF06705941E76");
            AdRequest request = builder.build();
            Logger.i("is test device? " + request.isTestDevice(this));
            mAdView.loadAd(request);
            ((LinearLayout) findViewById(R.id.ad_holder)).addView(mAdView);
        }
    }

    public abstract Proverb getItem(ProverbProvider provider);

    @Override
    public void onResume() {
        super.onResume();
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mAdView.resume();
        loadItem(this.mProverb);
        registerListeners();
    }

    /**
     * Loads proverb in corresponding fragment and update the view
     *
     * @param p proverb
     */
    public void loadItem(Proverb p) {
        this.mFragItem.load(p);
        this.mFragItem.updateView();
        this.mFragManager.setFavorite(p.isFavorite);

    }

    /**
     * Register listeners.
     * <p>It is supposed to be overridden by a subclass in order to have non-trivial behaviour.
     * It is called in {@link #onResume()} method.</p>
     */
    protected void registerListeners() {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        outState.putString(TEXT_TOKEN, this.mProverb.text);
        outState.putInt(ID_TOKEN, this.mProverb.id);
        outState.putBoolean(STATUS_TOKEN, this.mProverb.isFavorite);
    }

    @Override
    public void onPause() {
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mAdView.pause();
        if (this.shouldChangeStatus) {
            boolean newStatus = !this.mProverb.isFavorite;
            provider.setProverbStatus(this.mProverb.id, newStatus);
        }
        unregisterListeners();
        super.onPause();
    }


    /**
     * Unset previously registered listeners.
     * <p>It is called in {@link #onPause()} method.</p>
     */
    protected void unregisterListeners() {
    }

    @Override
    protected void onStop() {
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
//        this.mFragItem = null;
//        this.provider = null;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        this.mAdView.destroy();
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onDestroy();
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
            Intent intent = new Intent(getApplicationContext(), ActSettings.class);
            intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, NotificationTimeFragment.class.getName());
            startActivity(intent);
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
        this.mFragManager.setFavorite(this.shouldChangeStatus ? !mProverb.isFavorite : mProverb.isFavorite);
    }

    /**
     * It is called when a user wants to edit the proverb
     */
    @Override
    public void onEdit() {
        Intent intent = new Intent(getApplicationContext(), ActEdit.class);
        intent.putExtra(ActEdit.ID_TOKEN, mProverb.id);
        intent.putExtra(ActEdit.TEXT_TOKEN, mProverb.text);
        intent.putExtra(ActEdit.STATUS_TOKEN, mProverb.isFavorite);
        startActivityForResult(intent, EDIT_REQUEST);
    }

    /**
     * It is called when a user wants to share the proverb
     */
    @Override
    public void onShare() {
        Intent intent = new Intent(getApplicationContext(), ActShare.class);
        intent.putExtra(ActShare.TEXT_TOKEN, this.mProverb.text);
        intent.putExtra(ActShare.TITLE_TOKEN, getSharePostTitle());
        startActivity(intent);
    }

    /**
     * It is called when a user wants to delete the proverb
     */
    @Override
    public void onDelete() {
        Intent intent = new Intent(getApplicationContext(), ActDelete.class);
        intent.putExtra(ActDelete.ID_TOKEN, mProverb.id);
        intent.putExtra(ActDelete.TEXT_TOKEN, mProverb.text);
        intent.putExtra(ActDelete.STATUS_TOKEN, mProverb.isFavorite);
        startActivityForResult(intent, DELETE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.i(marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        if (requestCode == DELETE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent();
                intent.putExtra(ID_TOKEN, mProverb.id);
                setResult(RESULT_OK, intent);
                finish();
            }
            return;
        }
        if (requestCode == EDIT_REQUEST) {
            int id = mProverb.id;
            String text = data.getStringExtra(ActEdit.TEXT_TOKEN);
            boolean status = data.getBooleanExtra(ActEdit.STATUS_TOKEN, false);
            if (getCallingActivity() != null) {
                Intent intent = new Intent();
                intent.putExtra(ID_TOKEN, id);
                intent.putExtra(TEXT_TOKEN, text);
                intent.putExtra(STATUS_TOKEN, status);
                setResult(resultCode, intent);
                finish();
            } else {
                Logger.i("updating: " + id + ", " + text + ", " + status);
                this.mProverb = new Proverb(id, text, status);
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent());
        super.onBackPressed();
    }

}
