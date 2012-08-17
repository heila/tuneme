package com.touchlab.musicserver;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.touchlab.main.SongListAdapter;

public class LibraryActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_library);

		final ListView songListView = (ListView) findViewById(R.id.songlist);
		if (songListView.getAdapter() == null) {
			songListView.setAdapter(new SongListAdapter(this));
		}
		runOnUiThread(new Runnable() {
			public void run() {
				((BaseAdapter) songListView.getAdapter())
						.notifyDataSetChanged();
			}
		});

	}

}
