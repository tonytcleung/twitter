package com.tonytcleung.twitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
	private long				uid;
	private String 				name;
	private String 				screenName;
	private String 				profileImageURL;
	
	private static final String JSON_PARAM_UID					= "id";
	private static final String JSON_PARAM_NAME					= "name";
	private static final String JSON_PARAM_SCREEN_NAME			= "screen_name";
	private static final String JSON_PARAM__PROFILE_IMAGE_URL	= "profile_image_url";

	public User() {
		super();
	}
	
	/**
	 * factory method to return User from JSON
	 * @param json
	 * @return
	 */
	public static User fromJSON(JSONObject json) {
		User user					= new User();
		try {
			user.uid				= json.getLong(JSON_PARAM_UID);
			user.name				= json.getString(JSON_PARAM_NAME);
			user.screenName			= json.getString(JSON_PARAM_SCREEN_NAME);
			user.profileImageURL	= json.getString(JSON_PARAM__PROFILE_IMAGE_URL);
		}
		catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public long getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageURL() {
		return profileImageURL;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(uid);
		out.writeString(name);
		out.writeString(screenName);
		out.writeString(profileImageURL);
	}
	
	/**
	 * static variable for parcelable 
	 */
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
	
	/**
	 * constructer that is built using the parcel
	 * @param in
	 */
    private User(Parcel in) {
    	uid				= in.readLong();
    	name			= in.readString();
    	screenName		= in.readString();
    	profileImageURL	= in.readString();
    }
}
