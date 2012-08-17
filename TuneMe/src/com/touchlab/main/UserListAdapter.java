package com.touchlab.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.touchlab.musicserver.R;

/**
 * Keeps online list of users
 * 
 * @author heila
 * 
 */
public class UserListAdapter extends BaseAdapter {
	User[] onlineUsers = new User[0];
	Context context;

	public UserListAdapter(Context context) {
		super();
		this.context = context;
	}

	public void setUsers(User[] newUserList) {
		this.onlineUsers = newUserList;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.userrow, parent, false);

		TextView user = (TextView) rowView.findViewById(R.id.nameLabel);
		TextView desc = (TextView) rowView.findViewById(R.id.descriptionLabel);
		desc.setText(onlineUsers[position].description);
		ImageButton startButton = (ImageButton) rowView.findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Send an intent with the URL of the song to play. This is
				// expected by
				// MusicService.

				String songURL = onlineUsers[position].url
						+ onlineUsers[position].description;

			//	Intent i = new Intent();
			//Uri uri = Uri.parse(songURL);
				//i.setAction(android.content.Intent.ACTION_VIEW);

				// i.setDataAndType(uri, "audio/mp3");
				// //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				// | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				//context.startService(i);
				
				
				

				Intent i = new Intent(
						"com.example.android.musicplayer.action.URL");
				Uri uri = Uri.parse(songURL);
				i.setData(uri);
				context.startService(i);
				
			}
		});
		user.setText(onlineUsers[position].username);

		return rowView;
	}

	public int getCount() {
		return onlineUsers.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}
}
