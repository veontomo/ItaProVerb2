package com.veontomo.itaproverb.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Logger;
import com.veontomo.itaproverb.fragments.FragManagerPanel;

public class ActManagerPanel extends AppCompatActivity implements FragManagerPanel.ManagerPanelActions {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_manager_panel);
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
        Logger.i("action onStatusChange has yet to be implemented");
    }

    @Override
    public void onEdit() {
        /// TODO
        Logger.i("action onEdit has yet to be implemented");
    }

    @Override
    public void onShare() {
        /// TODO
        Logger.i("action onShare has yet to be implemented");
    }

    @Override
    public void onDelete() {
        /// TODO
        Logger.i("action onDelete has yet to be implemented");

    }
}
