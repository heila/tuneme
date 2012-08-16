package com.touchlab.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.touchlab.musicserver.DnssdDiscovery;
import com.touchlab.musicserver.R;

public class MainActivity extends Activity {

	ListView users;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Task().execute("");

		Intent i = new Intent(this, com.touchlab.musicserver.MusicService.class);
		i.setAction(com.touchlab.musicserver.MusicService.ACTION_START);
		startService(i);

		users = (ListView) findViewById(R.id.onlineUsers);
		User[] values = new User[3];
		values[0] = new User("heila", "", "");
		values[1] = new User("jan", "", "");
		values[2] = new User("rob", "", "");
		
		users.setAdapter(new UserListAdapter(this, values));

		Button b = (Button) findViewById(R.id.button);
		b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				User[] values = new User[2];
				values[0] = new User("Rob","","");
				values[1] = new User("Jan","","");
				((UserListAdapter)users.getAdapter()).setUsers(values);
			}
		});
		
		
	}

	private class Task extends AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			DnssdDiscovery sd = DnssdDiscovery.getInstance();
			sd.setUp(MainActivity.this);
			sd.publishUrl("Rob", "Music description goes here",
					"http://blah.blah/song");
			return -1;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
