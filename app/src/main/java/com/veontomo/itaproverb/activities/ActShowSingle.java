package com.veontomo.itaproverb.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.AppInit;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.fragments.FragShowSingle;

public class ActShowSingle extends AppCompatActivity {

    private FragShowSingle mFragShowSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show_single);
        AppInit.loadProverbs("proverbs.txt");
    }


    public void onStart(){
        super.onStart();
        this.mFragShowSingle = (FragShowSingle) getSupportFragmentManager().findFragmentById(R.id.act_show_single_frag_show_single);

        this.mFragShowSingle.display(new Proverb(34, "chi cerca trova"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ashow_single, menu);
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