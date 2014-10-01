package com.tonytcleung.twitter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Tweet implements Parcelable {
	private long				uid;
	private String 				body;
	private String 				createdAt;
	private User				user;
	
	private static final String	JSON_PARAM_UID			= "id";
	private static final String JSON_PARAM_BODY			= "text";
	private static final String JSON_PARAM_CREATED_AT	= "created_at";
	private static final String JSON_PARAM_USER			= "user";
	
	public Tweet() {
		super();
	}
	
	/**
	 * return a Tweet object from json
	 * @param json - JSONObject that represents a Tweet
	 * @return
	 */
	public static Tweet fromJSON(JSONObject json) {
		// Extract values form the json to populate the member variables
		Tweet tweet			= new Tweet();
		try {
			tweet.uid		= json.getLong(JSON_PARAM_UID);
			tweet.body		= json.getString(JSON_PARAM_BODY);
			tweet.createdAt	= json.getString(JSON_PARAM_CREATED_AT);
			tweet.user		= User.fromJSON(json.getJSONObject(JSON_PARAM_USER));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}
	
	/**
	 * factory method to return an ArrayList of tweets from a JSONArray
	 * @param jsonArray	- JSONArray that contains a list of json objects that represents a tweet
	 * @return
	 */
	public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets	= new ArrayList<Tweet>(jsonArray.length() );
		
		for (int i = 0; i < jsonArray.length(); i++ )  {
			try {
				tweets.add(Tweet.fromJSON(jsonArray.getJSONObject(i)));
			}
			catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
		}
		return tweets;
	}
	
	public long getUid() {
		return uid;
	}
	public String getBody() {
		return body;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public User getUser() {
		return user;
	}
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(uid);
		out.writeString(body);
		out.writeString(createdAt);
		out.writeParcelable(user, flags);
	}
	
	/**
	 * static variable for parcelable 
	 */
	public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
		@Override
		public Tweet createFromParcel(Parcel in) {
			return new Tweet(in);
		}

		@Override
		public Tweet[] newArray(int size) {
			return new Tweet[size];
		}
	};
	
	/**
	 * constructer that is built using the parcel
	 * @param in
	 */
    private Tweet(Parcel in) {
    	uid			= in.readLong();
    	body		= in.readString();
    	createdAt	= in.readString();
    	user		= in.readParcelable(User.class.getClassLoader());
    }
	
}

