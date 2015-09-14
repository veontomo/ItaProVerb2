package com.veontomo.itaproverb.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.itaproverb.R;

public class ActDelete extends AppCompatActivity {


    /**
     * name of the token under which it the proverb id is stored in the bundle
     */
    public static final String ID_TOKEN = "id";
    /**
     * name of the token under which the proverb text is stored in the bundle
     */
    public static final String TEXT_TOKEN = "text";
    /**
     * name of the token under which the proverb status is stored in the bundle
     */
    public static final String STATUS_TOKEN = "status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_delete);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
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
}
