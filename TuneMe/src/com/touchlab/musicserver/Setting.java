package com.touchlab.musicserver;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		
		Button save = (Button) findViewById(R.id.saveButton);
		save.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				SharedPreferences.Editor ed = getSharedPreferences("AppPrefs",
						MODE_PRIVATE).edit();
				ed.putString("Name", nameEdit.getText().toString());
				ed.commit();
				finish();
				
			}
		});
	}

	
}
