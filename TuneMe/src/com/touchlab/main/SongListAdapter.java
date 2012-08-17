package com.touchlab.main;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
		Button startButton = (Button) rowView.findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				DnssdDiscovery sd = DnssdDiscovery.getInstance();
				sd.publishUrl(MainActivity.username,
						"/" + songs[position].getSong(),
						"http://blah.blah/song");

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
}
