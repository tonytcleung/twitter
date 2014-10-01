package com.tonytcleung.twitter.Adapters;

import java.util.List;

import com.squareup.picasso.Picasso;
import com.tonytcleung.twitter.R;
import com.tonytcleung.twitter.models.Tweet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	// view cache
	private static class ViewHolder {
		ImageView ivProfileImage;
		TextView tvUserName;
		TextView tvBody;
	}
	
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// take the data source
		// get data item
		Tweet tweet						= getItem(position);
		
    	// use view holder pattern
    	ViewHolder viewHolder;
        // Check if an existing view is being reused, 
    	// if it is new, inflate the view and set viewHolder as tag
        if (convertView == null) {
        	viewHolder					= new ViewHolder();
        	convertView 				= LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
            viewHolder.ivProfileImage	= (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvUserName		= (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvBody			= (TextView) convertView.findViewById(R.id.tvBody);
            convertView.setTag(viewHolder);
        }
        // retrieve view holder from tag
        else {
        	viewHolder					= (ViewHolder) convertView.getTag();
        }
			
        // Populate the data into the template view using the data object
        viewHolder.tvUserName.setText(tweet.getUser().getScreenName());
        viewHolder.tvBody.setText(tweet.getBody());
        // reset the image from the recycled view
        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
        // ask for the photo to be added to the imageview based on the photo url
        // background: send network request from the url, download image bytes, convert into bitmamp, resizing the image, insert bitmap into the imageview
        // use icon as placeholder
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageURL()).placeholder(R.drawable.ic_launcher).into(viewHolder.ivProfileImage);
        
        // Return the completed view to render on screen
        return convertView;
	}

}
