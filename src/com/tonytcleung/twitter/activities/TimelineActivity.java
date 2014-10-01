package com.tonytcleung.twitter.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tonytcleung.twitter.R;
import com.tonytcleung.twitter.Adapters.TweetArrayAdapter;
import com.tonytcleung.twitter.listeners.EndlessScrollListener;
import com.tonytcleung.twitter.models.Tweet;
import com.tonytcleung.twitter.models.TwitterApplication;
import com.tonytcleung.twitter.models.TwitterClient;
import com.tonytcleung.twitter.models.User;

public class TimelineActivity extends Activity {
	
	public static final int		COMPOSE_INTENT_REQUEST_CODE			= 20;
	public static final String	INTENT_USER							= "USER";
	public static final String	INTENT_TWEET						= "TWEET";

	// number of cells that are not visible before kicking off server
	private static final int 	ENDLESS_SCROLL_VISIBLE_THRESHHOLD	= 10;
	
	private TwitterClient 		client;
	private User				user;
	private ArrayList<Tweet>	tweets;
	private ArrayAdapter<Tweet>	aTweets;
	private ListView			lvTweets;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client	= TwitterApplication.getRestClient();
		
		lvTweets	= (ListView) findViewById(R.id.lvTweets);
		tweets		= new ArrayList<Tweet>();
		aTweets		= new TweetArrayAdapter(this, tweets);
		
		lvTweets.setAdapter(aTweets);

        // add on scroll listenter for endless scroll with threshhold
		lvTweets.setOnScrollListener(new EndlessScrollListener(ENDLESS_SCROLL_VISIBLE_THRESHHOLD) {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// get last tweet  
				Tweet tweet = tweets.get(tweets.size() - 1);
				
				client.getHomeTimeline(String.valueOf(tweet.getUid()), null, new JsonHttpResponseHandler() {
					
					@Override
					public void onSuccess(JSONArray json) {
						ArrayList<Tweet> newTweets = Tweet.fromJSONArray(json);
						 // remove the first item if it is duplicate
						if (tweets.get(tweets.size()-1).getUid() == newTweets.get(0).getUid()) {
							newTweets.remove(newTweets.get(0));
							Log.d("Debug", newTweets.toString());
						}
						aTweets.addAll(newTweets);
					}
					
					@Override
					public void onFailure(Throwable error, String string) {
						Log.d("debug", error.toString());
						Log.d("debug", string.toString());
					}
				});
			}
		});
		
		getUserProfile();
		populateTimeline();
	}

	/**
	 * populate the listview with the timeline
	 */
	public void populateTimeline() {
		client.getHomeTimeline(null, null, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable error, String string) {
				Log.d("debug", error.toString());
				Log.d("debug", string.toString());
			}
		});
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
    	if (resultCode == RESULT_OK && requestCode == COMPOSE_INTENT_REQUEST_CODE) {
    		// grab the settings and save
    		Tweet tweet		= intent.getParcelableExtra(INTENT_TWEET);
    		tweets.add(0, tweet);
    		aTweets.notifyDataSetChanged();
    	}
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }
    
	public void onShowCompose(MenuItem menuItem) {
    	
		// launch the image display activity
		// create an intent
		Intent intent			= new Intent(this,  ComposeActivity.class);
		intent.putExtra(INTENT_USER	, user);
		// launch the new activity asking for result
		startActivityForResult(intent, COMPOSE_INTENT_REQUEST_CODE);
    }
}
