package org.kwansystems.droid.sensorlog;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SensorLogFragment extends Fragment implements OnCheckedChangeListener,SensorEventListener {
  private SensorManager mSensorManager;
  private TextView txtAccX,txtAccY,txtAccZ;
  private TextView txtBfldX,txtBfldY,txtBfldZ;
  private TextView txtGyroX,txtGyroY,txtGyroZ;
  Sensor acc,bfld,gyro;
  BubbleCompassFragment sibling;
  public SensorLogFragment(BubbleCompassFragment Lsibling) {
    sibling=Lsibling;
  }
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_sensor_log, container, false);
    ToggleButton btnSensors=(ToggleButton)rootView.findViewById(R.id.btnSensors);
    txtAccX=(TextView)rootView.findViewById(R.id.txtAccX);
    txtAccY=(TextView)rootView.findViewById(R.id.txtAccY);
    txtAccZ=(TextView)rootView.findViewById(R.id.txtAccZ);
    txtBfldX=(TextView)rootView.findViewById(R.id.txtBfldX);
    txtBfldY=(TextView)rootView.findViewById(R.id.txtBfldY);
    txtBfldZ=(TextView)rootView.findViewById(R.id.txtBfldZ);
    txtGyroX=(TextView)rootView.findViewById(R.id.txtGyroX);
    txtGyroY=(TextView)rootView.findViewById(R.id.txtGyroY);
    txtGyroZ=(TextView)rootView.findViewById(R.id.txtGyroZ);
    btnSensors.setOnCheckedChangeListener(this);
    mSensorManager = (SensorManager) (getActivity()).getSystemService(Context.SENSOR_SERVICE);
    acc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    bfld = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    gyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    return rootView;
  }
  @Override
  public void onCheckedChanged(CompoundButton btnGPS, boolean isChecked) {
    if(isChecked) {
    	mSensorManager.registerListener(this,acc, SensorManager.SENSOR_DELAY_NORMAL);
    	mSensorManager.registerListener(this,bfld,SensorManager.SENSOR_DELAY_NORMAL);
    	mSensorManager.registerListener(this,gyro,SensorManager.SENSOR_DELAY_NORMAL);
      mSensorManager.registerListener(sibling.mGraphView,acc, SensorManager.SENSOR_DELAY_NORMAL);
      mSensorManager.registerListener(sibling.mGraphView,bfld,SensorManager.SENSOR_DELAY_NORMAL);
      SensorLog.mService.openSensor();
    } else {
    	mSensorManager.unregisterListener(this);
      mSensorManager.unregisterListener(sibling.mGraphView);
      SensorLog.mService.closeSensor();
    }
  }
	@Override
  public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
  	
	}
  @Override
  public void onSensorChanged(SensorEvent arg0) {
    if(arg0.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
    	txtAccX.setText(String.format("%f",arg0.values[0]/arg0.sensor.getResolution()));
  	  txtAccY.setText(String.format("%f",arg0.values[1]/arg0.sensor.getResolution()));
    	txtAccZ.setText(String.format("%f",arg0.values[2]/arg0.sensor.getResolution()));
    } else if(arg0.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD) {
      txtBfldX.setText(String.format("%f",arg0.values[0]/arg0.sensor.getResolution()));
      txtBfldY.setText(String.format("%f",arg0.values[1]/arg0.sensor.getResolution()));
      txtBfldZ.setText(String.format("%f",arg0.values[2]/arg0.sensor.getResolution()));
    } else if(arg0.sensor.getType()==Sensor.TYPE_GYROSCOPE) {
      txtGyroX.setText(String.format("%f",arg0.values[0]/arg0.sensor.getResolution()));
      txtGyroY.setText(String.format("%f",arg0.values[1]/arg0.sensor.getResolution()));
      txtGyroZ.setText(String.format("%f",arg0.values[2]/arg0.sensor.getResolution()));
    }
	}
}