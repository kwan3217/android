package org.kwansystems.droid.sensorlog;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.*;
import android.content.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.*;
import android.location.GpsStatus.NmeaListener;
import android.os.*;
import android.widget.*;

public class SensorLogService extends Service implements NmeaListener,
    LocationListener, SensorEventListener {
  public void onNmeaReceived(long timestamp, String NMEA) {
    String ts = sdf.format(new Date(timestamp));
    if(oufNMEA!=null) oufNMEA.println(ts + NMEA);
  }

  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

  @Override
  public void onCreate() {

  }

  LocationManager locationManager;
  SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
  PrintWriter oufNMEA, oufSensor;

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    locationManager = (LocationManager) this
        .getSystemService(Context.LOCATION_SERVICE);
    locationManager.addNmeaListener(this);
    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    acc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    bfld = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    gyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    Toast.makeText(this, "Sensor service starting", Toast.LENGTH_SHORT).show();

    // If we get killed, after returning from here, restart
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    Toast.makeText(this, "Sensor service done", Toast.LENGTH_SHORT).show();
  }

  // Binder given to clients
  private final IBinder mBinder = new LocalBinder();

  /**
   * Class used for the client Binder. Because we know this service always runs
   * in the same process as its clients, we don't need to deal with IPC.
   */
  public class LocalBinder extends Binder {
    SensorLogService getService() {
      // Return this instance of LocalService so clients can call public
      // methods
      return SensorLogService.this;
    }
  }

  @Override
  public IBinder onBind(Intent intent) {
    return mBinder;
  }

  /** method for clients */
  public void openNMEA() {
    if(oufNMEA==null) try {
      Toast.makeText(this, "openNMEA", Toast.LENGTH_SHORT).show();
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
      oufNMEA = new PrintWriter(new FileWriter(Environment.getExternalStorageDirectory().getPath()+ "/SensorLogs/NMEA"+ sdf2.format(new Date()) + ".txt"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void closeNMEA() {
    Toast.makeText(this, "closeNMEA", Toast.LENGTH_SHORT).show();
    locationManager.removeUpdates(this);
    oufNMEA.close();
    oufNMEA=null;
  }

  private SensorManager mSensorManager;
  private Sensor acc, bfld, gyro;

  public void openSensor() {
    if(oufSensor==null) try {
      Toast.makeText(this, "openSensor", Toast.LENGTH_SHORT).show();
      mSensorManager.registerListener(this, acc,  SensorManager.SENSOR_DELAY_FASTEST);
      mSensorManager.registerListener(this, bfld, SensorManager.SENSOR_DELAY_FASTEST);
      mSensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_FASTEST);
      oufSensor = new PrintWriter(new FileWriter(Environment.getExternalStorageDirectory().getPath()+ "/SensorLogs/Sensor"+sdf2.format(new Date()) + ".csv"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void closeSensor() {
    if(oufSensor!=null) {
      mSensorManager.unregisterListener(this);
      Toast.makeText(this, "closeSensor", Toast.LENGTH_SHORT).show();
      oufSensor.close();
      oufSensor=null;
    }
  }

  @Override
  public void onLocationChanged(Location arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onProviderDisabled(String arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onProviderEnabled(String arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onAccuracyChanged(Sensor arg0, int arg1) {
    // TODO Auto-generated method stub

  }
  @Override
  public void onSensorChanged(SensorEvent arg0) {
    if(oufSensor!=null) {
      oufSensor.printf("%d,%s,%d,%f,%f,%f\n", arg0.timestamp, sdf.format(new Date()), arg0.sensor.getType(), arg0.values[0], arg0.values[1], arg0.values[2]);
    }
  }
}
