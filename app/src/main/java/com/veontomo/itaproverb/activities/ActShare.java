package com.veontomo.itaproverb.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;

/**
 * Contains buttons to share content on social networks.
 */
public class ActShare extends AppCompatActivity {
    /**
     * name of the token under which the proverb text is saved in a bundle
     */
    public static final String TEXT_TOKEN = "text";
    /**
     * name of the token under which the title text of the post to share
     * is saved in a bundle
     */
    public static final String TITLE_TOKEN = "title";
    /**
     * text of the proverb that this activity deals with.
     */
    private String mText;

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    /**
     * title with which the post is published
     */
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.act_share);
        Bundle b = savedInstanceState;
        if (b == null) {
            Intent intent = getIntent();
            if (intent != null) {
                b = intent.getExtras();
            }
        }
        if (b != null) {
            this.mText = b.getString(TEXT_TOKEN);
            this.mTitle = b.getString(TITLE_TOKEN);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        /// See
        /// http://stackoverflow.com/questions/29554085/android-facebook-api-and-sharelinkcontent
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

//        SharePhoto photo = new SharePhoto.Builder()
//                .setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.owl))
//                .build();
//        SharePhotoContent content = new SharePhotoContent.Builder()
//                .addPhoto(photo)
//                .build();
//        shareDialog.show(content);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(Config.FACEBOOK_URL))
                .setContentTitle(this.mTitle)
                .setImageUrl(Uri.parse(Config.LOGO_URL))
                .setContentDescription(this.mText)
                .build();

        ShareButton shareButton = (ShareButton) findViewById(R.id.act_share_fb);
        shareButton.setShareContent(content);
        shareButton.performClick();
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle b){
        b.putString(TEXT_TOKEN, this.mText);
        b.putString(TITLE_TOKEN, this.mTitle);
        super.onSaveInstanceState(b);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_facebook_share, menu);
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
