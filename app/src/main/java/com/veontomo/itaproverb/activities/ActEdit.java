package com.veontomo.itaproverb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.veontomo.itaproverb.R;

public class ActEdit extends AppCompatActivity {
    /**
     * opacity of the icon corresponding to a favorite proverb
     */
    private final float FAVORITE = 1f;

    /**
     * opacity of the icon corresponding to a non-favorite proverb
     */
    private final float NON_FAVORITE = 0.2f;
    /**
     * text of the proverb
     */
    private String mText;

    /**
     * proverb status (whether the proverb is favorite)
     */
    private boolean mStatus = false;

    /**
     * name of the token under which the proverb text is stored in the bundle
     */
    public static final String TEXT_TOKEN = "text";

    /**
     * name of the token under which the proverb status is stored in the bundle
     */
    public static final String STATUS_TOKEN = "status";
    /**
     * a view that contains the text of the proverb to modify
     */
    private EditText mInput;

    /**
     * click on this view confirms the action
     */
    private TextView mConfirm;

    /**
     * click on this view cancels the action
     */
    private TextView mCancel;
    /**
     * click on this view sets the proverb status
     */
    private View mStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit);

        Bundle b = savedInstanceState;
        if (b == null) {
            b = getIntent().getExtras();
        }
        if (b != null) {
            mText = b.getString(TEXT_TOKEN);
            mStatus = b.getBoolean(STATUS_TOKEN);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        this.mInput = (EditText) findViewById(R.id.act_edit_proverb_text);
        this.mConfirm = (TextView) findViewById(R.id.act_edit_confirm);
        this.mCancel = (TextView) findViewById(R.id.act_edit_cancel);
        this.mStatusView = findViewById(R.id.act_edit_favorite);

        if (this.mText != null) {
            this.mInput.setText(this.mText);
        }
        setFavorite(this.mStatus);
    }

    @Override
    public void onResume() {
        super.onResume();
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
                Intent intent = new Intent();
                intent.putExtra(TEXT_TOKEN, mInput.getEditableText().toString());
                intent.putExtra(STATUS_TOKEN, mStatus);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mStatusView.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                mStatus = !mStatus;
                setFavorite(mStatus);
            }
        });

    }

    @Override
    public void onPause() {
        mStatusView.setOnClickListener(null);
        mConfirm.setOnClickListener(null);
        mCancel.setOnClickListener(null);
        super.onPause();
    }

    @Override
    public void onStop() {
        this.mInput = null;
        this.mCancel = null;
        this.mConfirm = null;
        super.onStop();
    }


    @Override
    public void onSaveInstanceState(Bundle b) {
        b.putString(TEXT_TOKEN, this.mText);
        b.putBoolean(STATUS_TOKEN, this.mStatus);
        super.onSaveInstanceState(b);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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

    /**
     * Sets the opacity of the star.
     *
     * @param status
     */
    public void setFavorite(boolean status) {
        this.mStatusView.setAlpha(status ? FAVORITE : NON_FAVORITE);
    }
}
