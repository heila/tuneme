package com.touchlab.musicserver;

import java.io.IOException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Task().execute("Hallo");

		
		

	}

	private class Task extends AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			System.out.println("doInBackground");
			try {
				new MusicServer();
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
