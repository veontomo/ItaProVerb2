package com.veontomo.itaproverb.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.fragments.FragShowMulti;
import com.veontomo.itaproverb.fragments.FragShowSingle;

import java.util.ArrayList;
import java.util.List;

public class ActShowMulti extends AppCompatActivity {

    private FragShowMulti mFragShowMulti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show_multi);
    }

    public void onStart(){
        super.onStart();
        this.mFragShowMulti = (FragShowMulti) getSupportFragmentManager().findFragmentById(R.id.act_show_multi_fragment);
        List<Proverb> proverbs = new ArrayList<>();
        proverbs.add(new Proverb(1, "chi cerca trova"));
        proverbs.add(new Proverb(2, "una mela al giorno"));
        this.mFragShowMulti.load(proverbs);
        this.mFragShowMulti.updateView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_show_multi, menu);
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
