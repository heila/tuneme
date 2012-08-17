package com.touchlab.musicserver;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

public class Setting extends Activity {

	EditText nameEdit;
	String name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		SharedPreferences settings = getSharedPreferences("AppPrefs",
				MODE_PRIVATE);

		nameEdit = (EditText) findViewById(R.id.usernameEdit);

		name = settings.getString("Name", "Name");
		if (!name.equals("Name")) {
			nameEdit.setText(name);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SharedPreferences settings = getSharedPreferences("AppPrefs",
					MODE_PRIVATE);
			SharedPreferences.Editor ed = settings.edit();
			ed.putString("Name", nameEdit.getText().toString());
			ed.commit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
