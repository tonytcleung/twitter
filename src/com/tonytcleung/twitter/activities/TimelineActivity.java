package com.tonytcleung.twitter.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.tonytcleung.twitter.R;
import com.tonytcleung.twitter.fragments.HomeTimelineFragment;
import com.tonytcleung.twitter.fragments.MentionsTimelineFragment;
import com.tonytcleung.twitter.listeners.FragmentTabListener;
import com.tonytcleung.twitter.models.TwitterApplication;
import com.tonytcleung.twitter.models.TwitterClient;
import com.tonytcleung.twitter.models.User;

public class TimelineActivity extends FragmentActivity {
	
	public static final int		COMPOSE_INTENT_REQUEST_CODE			= 20;
	public static final String	INTENT_USER							= "USER";
	public static final String	INTENT_TWEET						= "TWEET";

	// number of cells that are not visible before kicking off server
	private static final int 	ENDLESS_SCROLL_VISIBLE_THRESHHOLD	= 10;
	
	private User				user;	
	private TwitterClient 		client;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		client	= TwitterApplication.getRestClient();
		getUserProfile();
		setupTabs();
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "first",
						HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "second",
			    		MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
	
	/**
	 * get the user profile
	 */
	public void getUserProfile() {
		client.getUserProfile(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				user = User.fromJSON(json);
				TimelineActivity.this.getActionBar().setTitle("@" + user.getScreenName());
			}
		
			@Override
			public void onFailure(Throwable error, String string) {
				Log.d("debug", error.toString());
				Log.d("debug", string.toString());
			}
		});
	}
	
    /**
     * add the tweet to first item of the array
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	// make sure that it is the same request code is the edit request code
//    	if (resultCode == RESULT_OK && requestCode == COMPOSE_INTENT_REQUEST_CODE) {
//    		// grab the settings and save
//    		Tweet tweet		= intent.getParcelableExtra(INTENT_TWEET);
//    		tweets.add(0, tweet);
//    		aTweets.notifyDataSetChanged();
//    	}
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }
	
	public void onProfileView(MenuItem menuItem) {
		Intent intent	= new Intent(this, ProfileActivity.class);
		intent.putExtra(INTENT_USER	, user);
		startActivity(intent);
	}
	
    /**
     * show compose
     * @param menuItem
     */
	public void onShowCompose(MenuItem menuItem) {
    	
		// launch the image display activity
		// create an intent
		Intent intent			= new Intent(this,  ComposeActivity.class);
		intent.putExtra(INTENT_USER	, user);
		// launch the new activity asking for result
		startActivityForResult(intent, COMPOSE_INTENT_REQUEST_CODE);
    }
}
