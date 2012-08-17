package com.touchlab.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.touchlab.musicserver.DnssdDiscovery;
import com.touchlab.musicserver.LocalContentItem;
import com.touchlab.musicserver.LocalContentLibrary;
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

				String songURL = onlineUsers[position].url
						+ onlineUsers[position].description;

				
				

				Intent i = new Intent(
						"com.example.android.musicplayer.action.URL");
				Uri uri = Uri.parse(songURL);
				i.setData(uri);
				context.startService(i);
				
				new WebTask(MainActivity.username,onlineUsers[position].username,onlineUsers[position].description).execute();
				
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
	
	
	private class WebTask extends AsyncTask<Integer, Integer, Void> {
		String localName;
		String listenerName;
		String songName;
		public WebTask(String localName, String listenerName, String songName) {
			this.localName = localName;
			this.listenerName = listenerName;
			this.songName = songName;				
		}

		@Override
		protected Void doInBackground(Integer... params) {
			String artist = "";
			String album = "";
			LocalContentItem item = LocalContentLibrary.getInstance().getLocalContent(this.songName);
			if (item != null) {
				artist = item.getArtist();
				album = item.getAlbum();
			}
			String url = "http://152.111.8.115/tuneme/?" + URLEncoder.encode("artist="+artist+"&album="+album+"&host_name="+localName+"&listener_name="+listenerName+"&song=" + songName);
			InputStream retStream = null;
			final HttpClient httpClient = new DefaultHttpClient();
			final HttpUriRequest request = new HttpGet(url);
			try {
				final HttpResponse resp = httpClient.execute(request);
				final int status = resp.getStatusLine().getStatusCode();
				if (HttpStatus.SC_OK == status) {
					retStream = resp.getEntity().getContent();
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
			if (retStream != null) {
				try {
					retStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return null;
		}
	}
	
}
