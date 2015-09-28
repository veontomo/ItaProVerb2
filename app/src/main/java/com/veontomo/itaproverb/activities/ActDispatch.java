package com.veontomo.itaproverb.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Notificator;

import bolts.AppLinks;

public class ActDispatch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dispatch);
        Config.loadProverbs(getApplicationContext());
        Notificator.start(getApplicationContext());

        FacebookSdk.sdkInitialize(getApplicationContext());
        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i(Config.APP_NAME, "App Link Target URL: " + targetUrl.toString());
        } else {
            Log.i(Config.APP_NAME, "App Link Target URL is null ");
        }
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        long notification_time = pref.getLong("notification_time", -1l);
        Log.i(Config.APP_NAME, "from preference: " + notification_time);
    }

    @Override
    public void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
        TextView proverbDay = (TextView) findViewById(R.id.dispatcher_proverb_day);
        TextView proverbOracle = (TextView) findViewById(R.id.dispatcher_proverb_oracle);
        TextView proverbFavorites = (TextView) findViewById(R.id.dispatcher_favorites);
        TextView proverbAll = (TextView) findViewById(R.id.dispatcher_all_proverb);
        proverbDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActProverbDay.class);
                startActivity(intent);
            }
        });
        proverbOracle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActOracle.class);
                startActivity(intent);
            }
        });
        proverbFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActFavoriteProverbs.class);
                startActivity(intent);
            }
        });
        proverbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActAllProverbs.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dispatch, menu);
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
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
