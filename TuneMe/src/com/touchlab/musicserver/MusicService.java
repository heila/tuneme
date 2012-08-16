package com.touchlab.musicserver;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;

/**
 * Listens for Intents to start/stop the music server
 * 
 * @author Heila van der Merwe
 * 
 * 
 * 
 */
public class MusicService extends Service {

	public static final String ACTION_START = "com.example.android.musicserver.action.START";
	public static final String ACTION_STOP = "com.example.android.musicserver.action.STOP";

	// need lock...
	public boolean running = false;

	MusicServer server;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * Called when we receive an Intent. When we receive an intent sent to us
	 * via startService(), this is the method that gets called. So here we react
	 * appropriately depending on the Intent's action, which specifies what is
	 * being requested of us.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String action = intent.getAction();
		if (action.equals(ACTION_START)) {
			if (running == false) {
				new StartTask().execute("");
			}
		} else if (action.equals(ACTION_STOP)) {
			if (server != null && running) {
				running = false;
				server.stop();
			}

		}
		return START_NOT_STICKY; // Means we started the service, but don't want
									// it to
									// restart in case it's killed.
	}

	private class StartTask extends AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			try {
				File baseDirectory = Environment.getExternalStorageDirectory();
				server = new MusicServer(baseDirectory);
				running = true;
			} catch (IOException ioe) {
				System.err.println("Couldn't start server:\n" + ioe);
				System.exit(-1);
			}
			System.out.println("Listening on port 8080. Hit Enter to stop.\n");
			try {
				System.in.read();
			} catch (Throwable t) {
			}

			return -1;

		}
	}

}
