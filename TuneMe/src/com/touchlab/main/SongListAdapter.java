package com.touchlab.main;

import java.util.ArrayList;

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
public class SongListAdapter extends BaseAdapter {
	LocalContentItem[] songs;
	Context context;
	LocalContentLibrary lib = LocalContentLibrary.getInstance();

	public SongListAdapter(Context context) {
		super();
		this.context = context;
		ArrayList<LocalContentItem> it = lib.getContents();
		songs = new LocalContentItem[it.size()];
		for (int c = 0; c < it.size(); c++) {
			songs[c] = it.get(c);
		}

	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.userrow, parent, false);

		TextView user = (TextView) rowView.findViewById(R.id.nameLabel);
		TextView desc = (TextView) rowView.findViewById(R.id.descriptionLabel);
		desc.setText(songs[position].getAlbum());
		ImageButton startButton = (ImageButton) rowView
				.findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				(new Task()).execute(position);
				((Activity) context).finish();

			}
		});
		user.setText(songs[position].getSong());

		return rowView;
	}

	public int getCount() {
		return songs.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	private class Task extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			DnssdDiscovery sd = DnssdDiscovery.getInstance();
			sd.publishUrl(MainActivity.username,
					"/" + songs[params[0]].getLocalAccessUrl(),
					"http://blah.blah/song");

			((Activity) SongListAdapter.this.context)
					.runOnUiThread(new Runnable() {
						public void run() {
							((BaseAdapter) SongListAdapter.this)
									.notifyDataSetChanged();
						}
					});

			String songURL = "http://localhost:8080/"
					+ songs[params[0]].getLocalAccessUrl();

			Intent i = new Intent("com.example.android.musicplayer.action.URL");
			Uri uri = Uri.parse(songURL);
			i.setData(uri);
			context.startService(i);

			return null;
		}
	}
}
