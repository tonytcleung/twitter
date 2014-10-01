package com.tonytcleung.twitter.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.tonytcleung.twitter.R;
import com.tonytcleung.twitter.models.Tweet;
import com.tonytcleung.twitter.models.TwitterApplication;
import com.tonytcleung.twitter.models.TwitterClient;
import com.tonytcleung.twitter.models.User;

public class ComposeActivity extends Activity {
	private User		user;
	private ImageView	ivComposeProfile;
	private TextView	tvComposeName;
	private TextView	tvComposeScreenName;
	private EditText	etComposeMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		// retrieve user info
		user	= (User) getIntent().getParcelableExtra(TimelineActivity.INTENT_USER);
		setupViews();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet, menu);
        return true;
    }
	
	/**
	 * setup the imageView, text view and edit texts
	 */
	private void setupViews() {
		ivComposeProfile	= (ImageView) findViewById(R.id.ivComposeProfile);
		tvComposeName		= (TextView) findViewById(R.id.tvComposeName);
		tvComposeScreenName	= (TextView) findViewById(R.id.tvComposeScreenName);
		etComposeMessage	= (EditText) findViewById(R.id.etComposeMessage);
		
        Picasso.with(this).load(user.getProfileImageURL()).placeholder(R.drawable.ic_launcher).into(ivComposeProfile);
        tvComposeName.setText(user.getName());
        tvComposeScreenName.setText("@" + user.getScreenName());
	}
	
	/**
	 * submit button is pressed, call rest client to submit
	 * @param menuItem
	 */
	public void onSubmitUpdate(MenuItem menuItem) {
		String status	= etComposeMessage.getText().toString();

		TwitterClient client = TwitterApplication.getRestClient();
		client.updateStatus(status, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject json) {
				Tweet tweet	= Tweet.fromJSON(json);
				
				// send settings back in intent
				Intent intent			= new Intent();
				intent.putExtra(TimelineActivity.INTENT_TWEET, tweet);

				setResult(RESULT_OK, intent);
				finish();
			}
			
			@Override
			public void onFailure(Throwable error, String string) {
				Log.d("debug", error.toString());
				Log.d("debug", string.toString());
			}
		});
	}
}
