package com.tonytcleung.twitter.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tonytcleung.twitter.R;
import com.tonytcleung.twitter.fragments.UserTimelineFragment;
import com.tonytcleung.twitter.models.User;

public class ProfileActivity extends FragmentActivity {

	User					user;
	ImageView				ivProfileImage;
	UserTimelineFragment	userTimelineFragment;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		user						= (User) getIntent().getParcelableExtra(TimelineActivity.INTENT_USER);
		// Within the activity
		FragmentTransaction transaction	= getSupportFragmentManager().beginTransaction();
		UserTimelineFragment fragment = UserTimelineFragment.newInstance(user);
		transaction.replace(R.id.flUserTimeline, fragment);
		transaction.commit();
//		userTimelineFragment		= (UserTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
//		userTimelineFragment.user	= user;
//		userTimelineFragment.populateTimeline();
		populateProfileHeader();
	}


	/**
	 * populates the profile header with the user
	 */
	private void populateProfileHeader() {
		ImageView	ivProfileImage		= (ImageView) findViewById(R.id.ivProfileImage);
		TextView	tvName				= (TextView) findViewById(R.id.tvName);
		TextView	tvTagline			= (TextView) findViewById(R.id.tvTagline);
		TextView	tvFollowers			= (TextView) findViewById(R.id.tvFollowers);
		TextView	tvFollowing			= (TextView) findViewById(R.id.tvFollowing);
		
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagLine());
		tvFollowers.setText(String.valueOf(user.getFollowersCount()) + " Followers");
		tvFollowing.setText(String.valueOf(user.getFollowingCount()) + " Following");
		
        Picasso.with(this).load(user.getProfileImageURL()).placeholder(R.drawable.ic_launcher).into(ivProfileImage);

	}


}
