package com.tonytcleung.twitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
	private long				uid;
	private String 				name;
	private String 				screenName;
	private String				tagLine;
	private String 				profileImageURL;
	private int					followersCount;
	private int					followingCount;
	
	private static final String JSON_PARAM_UID					= "id";
	private static final String JSON_PARAM_NAME					= "name";
	private static final String JSON_PARAM_SCREEN_NAME			= "screen_name";
	private static final String JSON_PARAM_TAG_LINE				= "description";
	private static final String JSON_PARAM_PROFILE_IMAGE_URL	= "profile_image_url";
	private static final String JSON_PARAM_FOLLOWERS_COUNT		= "followers_count";
	private static final String JSON_PARAM_FOLLOWING_COUNT		= "friends_count";
	

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
			user.tagLine			= json.getString(JSON_PARAM_TAG_LINE);
			user.profileImageURL	= json.getString(JSON_PARAM_PROFILE_IMAGE_URL);
			user.followersCount		= json.getInt(JSON_PARAM_FOLLOWERS_COUNT);
			user.followingCount		= json.getInt(JSON_PARAM_FOLLOWING_COUNT);
		}
		catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public String getTagLine() {
		return tagLine;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFollowingCount() {
		return followingCount;
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
		out.writeString(tagLine);
		out.writeString(profileImageURL);
		out.writeInt(followersCount);
		out.writeInt(followingCount);
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
    	tagLine			= in.readString();
    	profileImageURL	= in.readString();
    	followersCount	= in.readInt();
    	followingCount	= in.readInt();
    }
}
