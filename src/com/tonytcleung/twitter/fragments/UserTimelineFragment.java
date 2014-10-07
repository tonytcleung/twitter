package com.tonytcleung.twitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.tonytcleung.twitter.models.Tweet;
import com.tonytcleung.twitter.models.TwitterApplication;
import com.tonytcleung.twitter.models.TwitterClient;
import com.tonytcleung.twitter.models.User;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserTimelineFragment extends TweetsListFragment {
	
	private TwitterClient 		client;
	public	User				user;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		client	= TwitterApplication.getRestClient();
		populateTimeline();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/**
	 * populate the listview with the timeline
	 */
	public void populateTimeline() {
		String uid	= null;
		if (null != user){
			uid	= String.valueOf(user.getUid());
		}
		
		client.getUserTimeline(uid, null, null, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray jsonArray) {
				removeAll();
				addAll(Tweet.fromJSONArray(jsonArray));
			}
			
			@Override
			public void onFailure(Throwable error, String string) {
				Log.d("debug", error.toString());
				Log.d("debug", string.toString());
			}
		});
	}
	
	/**
	 * load more tweets and append to array
	 */
	public void loadMore() {
		// get last tweet  
		Tweet tweet = tweets.get(tweets.size() - 1);
		
		client.getUserTimeline(String.valueOf(user.getUid()), String.valueOf(tweet.getUid()), null, new JsonHttpResponseHandler() {
			
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
}
