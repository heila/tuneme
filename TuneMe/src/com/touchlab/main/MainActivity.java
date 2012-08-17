package com.touchlab.main;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.touchlab.musicserver.DnssdDiscovery;
import com.touchlab.musicserver.DnssdDiscovery.DiscoveryChange;
import com.touchlab.musicserver.R;

public class MainActivity extends Activity implements DiscoveryChange {

	ListView userListView;
	static String username;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startDiscovery();

		Intent i = new Intent(this, com.touchlab.musicserver.MusicService.class);
		i.setAction(com.touchlab.musicserver.MusicService.ACTION_START);
		startService(i);

		userListView = (ListView) findViewById(R.id.onlineUsers);
		userListView.setAdapter(new UserListAdapter(this));

	}

	private void startDiscovery() {
		final SharedPreferences settings = getSharedPreferences("AppPrefs",
				MODE_PRIVATE);
		username = settings.getString("Name", "Name");

		if (username.equals("Name")) {
			// ask for new username via AlertDialog
			final EditText input = new EditText(MainActivity.this);
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("Set username")
					.setMessage("Please enter an username")
					.setView(input)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									username = input.getText().toString();
									SharedPreferences.Editor prefEditor = settings
											.edit();
									prefEditor.putString("Name", username);
									prefEditor.commit();
									new StartDiscoveryTask().execute();
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									username = "Name";
								}
							}).show();
		} else
			new StartDiscoveryTask().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(this,
					com.touchlab.musicserver.Setting.class));
			return true;
		case R.id.menu_stop_server:
			Intent i = new Intent(this,
					com.touchlab.musicserver.MusicService.class);
			i.setAction(com.touchlab.musicserver.MusicService.ACTION_STOP);
			startService(i);
			return true;
		case R.id.menu_refresh:
			discoveryChangeNotification();
			return true;
		case R.id.menu_library:
			startActivity(new Intent(this,
					com.touchlab.musicserver.LibraryActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Called when the the list of serverinfo's changes. The list has to be
	 * parsed from the beginning. What happens when the item currently listing
	 * to disappears? We have to reselect the item in the list currently active.
	 */
	public void discoveryChangeNotification() {
		DnssdDiscovery ds = DnssdDiscovery.getInstance();
		ArrayList<javax.jmdns.ServiceInfo> infos = ds.getServicesInfo();
		// User i = (User) userListView.getSelectedItem(); // store currently
		// selected
		// item
		User[] newUsers = new User[infos.size()];
		for (int c = 0; c < infos.size(); c++) {
			User u = new User(infos.get(c));
			newUsers[c] = u;
		}
		((UserListAdapter) userListView.getAdapter()).setUsers(newUsers);
		// if(newUsers.contains(i)){
		// userListView.
		// userListView.setSelection(position)
		// }
		runOnUiThread(new Runnable() {
			public void run() {
				((BaseAdapter) userListView.getAdapter())
						.notifyDataSetChanged();
			}
		});

	}

	private class StartDiscoveryTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			DnssdDiscovery sd = DnssdDiscovery.getInstance();
			sd.setUp(MainActivity.this);
			sd.setNotificationCallback(MainActivity.this);

			sd.publishUrl(username, "/prelude.mp3",
					"Prelude");
			return null;
		}
	}
	
	
	
	

}
