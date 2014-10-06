package com.tonytcleung.twitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.tonytcleung.twitter.models.Tweet;
import com.tonytcleung.twitter.models.TwitterApplication;
import com.tonytcleung.twitter.models.TwitterClient;

public class MentionsTimelineFragment extends TweetsListFragment {

	private TwitterClient 		client;
	
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
		client.getMentionsTimeline(null, null, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray jsonArray) {
				addAll(Tweet.fromJSONArray(jsonArray));
			}
			
			@Override
			public void onFailure(Throwable error, String string) {
				Log.d("debug", error.toString());
				Log.d("debug", string.toString());
			}
		});
	}
}
