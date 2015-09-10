package com.veontomo.itaproverb.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.fragments.FragManagerPanel;

public class ActManager extends AppCompatActivity implements FragManagerPanel.ManagerPanelActions {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_manager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manager, menu);
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
        Log.i(Config.APP_NAME, "action onStatusChange has yet to be implemented");
    }

    @Override
    public void onEdit() {
        /// TODO
        Log.i(Config.APP_NAME, "action onEdit has yet to be implemented");
    }

    @Override
    public void onShare() {
        /// TODO
        Log.i(Config.APP_NAME, "action onShare has yet to be implemented");
    }

    @Override
    public void onDelete() {
        /// TODO
        Log.i(Config.APP_NAME, "action onDelete has yet to be implemented");

    }
}
