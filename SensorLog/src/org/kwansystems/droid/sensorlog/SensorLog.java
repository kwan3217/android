package org.kwansystems.droid.sensorlog;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SensorLog extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_log);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sensor_log, menu);
		return true;
	}

}
