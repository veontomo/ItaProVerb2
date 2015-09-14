package com.veontomo.itaproverb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.veontomo.itaproverb.R;

public class ActEdit extends AppCompatActivity {
    /**
     * text of the proverb
     */
    private String mText;

    /**
     * name of the token under which the proverb text is stored in the bundle
     */
    public static final String TEXT_TOKEN = "text";
    /**
     * a view that contains the text of the proverb to modify
     */
    private EditText mInput;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mText = b.getString(TEXT_TOKEN);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mInput = (EditText) findViewById(R.id.act_edit_proverb_text);
        this.mConfirm = (ImageView) findViewById(R.id.act_delete_confirm);
        this.mCancel = (ImageView) findViewById(R.id.act_delete_cancel);

        if (this.mText != null) {
            this.mInput.setText(this.mText);
        }
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
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public void onStop() {
        this.mInput = null;
        this.mCancel = null;
        this.mConfirm = null;
        super.onStop();
    }

    @Override
    public void onPause() {
        mConfirm.setOnClickListener(null);
        mCancel.setOnClickListener(null);
        super.onPause();
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
}
