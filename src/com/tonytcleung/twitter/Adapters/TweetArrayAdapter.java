package com.tonytcleung.twitter.Adapters;

import java.util.List;

import com.squareup.picasso.Picasso;
import com.tonytcleung.twitter.R;
import com.tonytcleung.twitter.models.Tweet;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	
	// view cache
	private static class ViewHolder {
		ImageView ivProfileImage;
		TextView tvUserName;
		TextView tvBody;
		TextView tvCreated;
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
            viewHolder.tvUserName		= (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvBody			= (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.tvBody.setMovementMethod(LinkMovementMethod.getInstance());
            viewHolder.tvCreated		= (TextView) convertView.findViewById(R.id.tvCreated);
            

            viewHolder.ivProfileImage	= (ImageView) convertView.findViewById(R.id.ivProfileImage);
//            viewHolder.ivProfileImage.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					
//				}
//			})
            
            
            convertView.setTag(viewHolder);
        }
        // retrieve view holder from tag
        else {
        	viewHolder					= (ViewHolder) convertView.getTag();
        }
        
        // set the ivProfileImage tag to the user
        viewHolder.ivProfileImage.setTag(tweet.getUser());
        // Populate the data into the template view using the data object
        viewHolder.tvUserName.setText(tweet.getUser().getScreenName());
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.tvCreated.setText(tweet.getCreatedAt());
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
