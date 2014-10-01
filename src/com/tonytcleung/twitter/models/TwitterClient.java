package com.tonytcleung.twitter.models;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS 	= TwitterApi.class;
	public static final String REST_URL 						= "https://api.twitter.com/1.1"; 
	public static final String REST_CONSUMER_KEY 				= "INvy4sewirVM3P1zYVQwJtuCr";       
	public static final String REST_CONSUMER_SECRET	 			= "w6YgyCcAdRtcNM6E83Rdq88a7yChveiuknO8nfygLxYTqVXYri"; 
	public static final String REST_CALLBACK_URL 				= "oauth://cpbasictweets"; // Change this (here and in manifest)
	
	// verify credentials
	private static final String REST_VERIFY_CRED_URL					= "account/verify_credentials.json";
	private static final String REST_VERIFY_CRED_INCLUDE_ENTITIES_KEY	= "include_entities";
	private static final String REST_VERIFY_CRED_INCLUDE_ENTITIES_VALUE	= "false";
	private static final String REST_VERIFY_CRED_SKIP_STATUS_KEY		= "skip_status";
	private static final String REST_VERIFY_CRED_SKIP_STATUS_VALUE		= "true";
	
	// getHomTimeLine
	private static final String REST_TIMELINE_URL				= "statuses/home_timeline.json";
	private static final String REST_TIMELINE_COUNT				= "200";
	private static final String REST_TIMELINE_COUNT_KEY			= "count";
	
	// tweet
	private static final String REST_UPDATE_STATUS_URL			= "statuses/update.json";
	private static final String REST_UPDATE_STATUS_STATUS_KEY	= "status";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	/**
	 * method to retrieve home timeline
	 * @param handler - http handler that manages the connection
	 */
	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl(REST_TIMELINE_URL);
		RequestParams params	= new RequestParams();
		params.put(REST_TIMELINE_COUNT_KEY, REST_TIMELINE_COUNT);
		client.get(apiURL, params, handler);
	}
	
	/**
	 * method to get user profile by verifying credentials
	 * @param handler - http handler that manages the connection
	 */
	public void getUserProfile(AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl(REST_VERIFY_CRED_URL);
		RequestParams params	= new RequestParams();
		params.put(REST_VERIFY_CRED_INCLUDE_ENTITIES_KEY, REST_VERIFY_CRED_INCLUDE_ENTITIES_VALUE);
		params.put(REST_VERIFY_CRED_SKIP_STATUS_KEY, REST_VERIFY_CRED_SKIP_STATUS_VALUE);
		client.get(apiURL, params, handler);
	}
	
	/**
	 * send a tweet to your timeline by updating status
	 * @param handler - http handler that manages the connection
	 * @param message - the status to be tweeted
	 */
	public void updateStatus(String message, AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl(REST_UPDATE_STATUS_URL);
		RequestParams params	= new RequestParams();
		params.put(REST_UPDATE_STATUS_STATUS_KEY, message);
		client.post(apiURL, params, handler);
	}
}