package com.veontomo.itaproverb.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;

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

    /**
     * id of the proverb
     */
    private int mId;

    /**
     * text of the proverb
     */
    private String mText;

    /**
     * status of the proverb
     */
    private boolean mStatus;
    /**
     * Text view that contains the proverb text
     */
    private TextView mProverbTextView;
    /**
     * a view click on which confirms the action
     */
    private ImageView mConfirm;

    /**
     * a view click on which cancels the action
     */
    private ImageView mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!Config.PRODUCTION_MODE) {
            Config.strictModeInit();
        }
        super.onCreate(savedInstanceState);
        Log.i(Config.APP_NAME, "activity delete: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        setContentView(R.layout.act_delete);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            Log.i(Config.APP_NAME, "bundle is not null");
            mId = b.getInt(ID_TOKEN, -1);
            mText = b.getString(TEXT_TOKEN);
            mStatus = b.getBoolean(STATUS_TOKEN);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mProverbTextView = (TextView) findViewById(R.id.act_delete_proverb_text);
        mConfirm = (ImageView) findViewById(R.id.act_edit_confirm);
        mCancel = (ImageView) findViewById(R.id.act_edit_cancel);


    }

    @Override
    public void onResume() {
        super.onResume();
        if (mProverbTextView != null) {
            mProverbTextView.setText(mText);
        }
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void onPause() {
        mConfirm.setOnClickListener(null);
        mCancel.setOnClickListener(null);
        super.onPause();
    }

    @Override
    public void onStop() {
        mCancel = null;
        mConfirm = null;
        mProverbTextView = null;

        super.onStop();
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
