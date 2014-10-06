package com.tonytcleung.twitter.fragments;


import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.tonytcleung.twitter.R;
import com.tonytcleung.twitter.Adapters.TweetArrayAdapter;
import com.tonytcleung.twitter.listeners.EndlessScrollListener;
import com.tonytcleung.twitter.models.Tweet;

public class TweetsListFragment extends Fragment {
	
	private ArrayList<Tweet>	tweets;
	private ArrayAdapter<Tweet>	aTweets;
	protected ListView			lvTweets;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tweets		= new ArrayList<Tweet>();
		aTweets		= new TweetArrayAdapter(getActivity(), tweets);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflate the layout
		View view	= inflater.inflate(R.layout.fragment_tweets_list, container, false);
		// assign view references
		lvTweets	= (ListView) view.findViewById(R.id.lvTweets);
		
		lvTweets.setAdapter(aTweets);

		// return view
		return view;
	}
	
	/**
	 * add the given array list of tweets to the array adapter
	 * @param tweets
	 */
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
}
