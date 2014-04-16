package org.kwansystems.droid.sensorlog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.kwansystems.droid.sensorlog.R;
import org.kwansystems.droid.sensorlog.SensorLogService.LocalBinder;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SensorLog extends FragmentActivity implements ActionBar.TabListener {
  SectionsPagerAdapter mSectionsPagerAdapter;
  ViewPager mViewPager;
  static protected SensorLogService mService;
  protected boolean mBound;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	Intent intent=new Intent(this,SensorLogService.class);
    startService(intent);
    bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sensor_log);

    // Set up the action bar.
    final ActionBar actionBar = getActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    // Create the adapter that will return a fragment for each of the three
    // primary sections of the app.
    mSectionsPagerAdapter = new SectionsPagerAdapter(
        getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    // When swiping between different sections, select the corresponding
    // tab. We can also use ActionBar.Tab#select() to do this if we have
    // a reference to the Tab.
    mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        actionBar.setSelectedNavigationItem(position);
      }
    });

    // For each of the sections in the app, add a tab to the action bar.
    for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
      // Create a tab with text corresponding to the page title defined by
      // the adapter. Also specify this Activity object, which implements
      // the TabListener interface, as the callback (listener) for when
      // this tab is selected.
      actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.sensor_log, menu);
    return true;
  }

  @Override
  public void onTabSelected(ActionBar.Tab tab,
      FragmentTransaction fragmentTransaction) {
    // When the given tab is selected, switch to the corresponding page in
    // the ViewPager.
    mViewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(ActionBar.Tab tab,
      FragmentTransaction fragmentTransaction) {
  }

  @Override
  public void onTabReselected(ActionBar.Tab tab,
      FragmentTransaction fragmentTransaction) {
  }

  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
   * one of the sections/tabs/pages.
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter {
    String pageTitle[];
    Fragment fragment[];
    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
      pageTitle=new String[] {
          getString(R.string.title_section1),
          getString(R.string.title_section2),
          getString(R.string.title_section3),
      };
      fragment=new Fragment[] {new GPSLogFragment(),new SensorInfoFragment(),new SensorLogFragment()};
    }

    @Override
    public Fragment getItem(int position) {
      return fragment[position];
    }

    @Override
    public int getCount() {
      return pageTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      Locale l = Locale.getDefault();
      return pageTitle[position].toUpperCase(l);
    }
  }

  public static class GPSLogFragment extends Fragment implements NmeaListener,LocationListener,OnCheckedChangeListener {
    TextView[] txtSystemClock,txtNMEA;
    SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss.SSS");
    ArrayList<String> l=new ArrayList<String>();
    LocationManager locationManager;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public GPSLogFragment() {
    }

    @Override
    public void onLocationChanged(Location loc) {}
    @Override
    public void onProviderDisabled(String provider) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onStatusChanged(String provider, int status,Bundle extras) {} 

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      View rootView = inflater.inflate(
          R.layout.fragment_gps_log, container, false);
      txtSystemClock=new TextView[] {
          (TextView)rootView.findViewById(R.id.txtSystemClock00),
          (TextView)rootView.findViewById(R.id.txtSystemClock01),
          (TextView)rootView.findViewById(R.id.txtSystemClock02),
          (TextView)rootView.findViewById(R.id.txtSystemClock03),
          (TextView)rootView.findViewById(R.id.txtSystemClock04),
          (TextView)rootView.findViewById(R.id.txtSystemClock05),
          (TextView)rootView.findViewById(R.id.txtSystemClock06),
          (TextView)rootView.findViewById(R.id.txtSystemClock07),
          (TextView)rootView.findViewById(R.id.txtSystemClock08),
          (TextView)rootView.findViewById(R.id.txtSystemClock09),
          (TextView)rootView.findViewById(R.id.txtSystemClock10),
          (TextView)rootView.findViewById(R.id.txtSystemClock11),
          (TextView)rootView.findViewById(R.id.txtSystemClock12),
          (TextView)rootView.findViewById(R.id.txtSystemClock13),
          (TextView)rootView.findViewById(R.id.txtSystemClock14),
          (TextView)rootView.findViewById(R.id.txtSystemClock15),
          (TextView)rootView.findViewById(R.id.txtSystemClock16),
          (TextView)rootView.findViewById(R.id.txtSystemClock17),
          (TextView)rootView.findViewById(R.id.txtSystemClock18),
          (TextView)rootView.findViewById(R.id.txtSystemClock19)
      };
      txtNMEA=new TextView[] {
          (TextView)rootView.findViewById(R.id.txtNMEA00),		
          (TextView)rootView.findViewById(R.id.txtNMEA01),		
          (TextView)rootView.findViewById(R.id.txtNMEA02),		
          (TextView)rootView.findViewById(R.id.txtNMEA03),		
          (TextView)rootView.findViewById(R.id.txtNMEA04),		
          (TextView)rootView.findViewById(R.id.txtNMEA05),		
          (TextView)rootView.findViewById(R.id.txtNMEA06),		
          (TextView)rootView.findViewById(R.id.txtNMEA07),		
          (TextView)rootView.findViewById(R.id.txtNMEA08),		
          (TextView)rootView.findViewById(R.id.txtNMEA09),		
          (TextView)rootView.findViewById(R.id.txtNMEA10),		
          (TextView)rootView.findViewById(R.id.txtNMEA11),		
          (TextView)rootView.findViewById(R.id.txtNMEA12),		
          (TextView)rootView.findViewById(R.id.txtNMEA13),		
          (TextView)rootView.findViewById(R.id.txtNMEA14),		
          (TextView)rootView.findViewById(R.id.txtNMEA15),		
          (TextView)rootView.findViewById(R.id.txtNMEA16),		
          (TextView)rootView.findViewById(R.id.txtNMEA17),		
          (TextView)rootView.findViewById(R.id.txtNMEA18),		
          (TextView)rootView.findViewById(R.id.txtNMEA19),		
      };
      ToggleButton btnGPS=(ToggleButton)rootView.findViewById(R.id.btnGPS);
      btnGPS.setOnCheckedChangeListener(this);
      // Register the listener with the Location Manager to receive location updates
      // Acquire a reference to the system Location Manager

      locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
      locationManager.addNmeaListener(this);
      
      return rootView;
    }

    @Override
    public void onNmeaReceived(long timestamp, String NMEA) {
      String type=NMEA.substring(1,6);
      if(type.equals("GPGSV")||type.equals("GLGSV"))  type=NMEA.substring(1,10);
      int i=l.indexOf(type);
      if(i<0) {
        l.add(type);
        i=l.indexOf(type);
      }
      if(i<txtNMEA.length) {
        txtNMEA[i].setText(NMEA);
        String ts=sdf.format(new Date(timestamp));
        txtSystemClock[i].setText(ts);
      }
    }

    @Override
    public void onCheckedChanged(CompoundButton btnGPS, boolean isChecked) {
      if(isChecked) {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        mService.openNMEA();
      } else {
        locationManager.removeUpdates(this);
        mService.closeNMEA();
      }
    }
  }

  public static class SensorInfoFragment extends Fragment {
    private SensorManager mSensorManager;
    private TextView txtSensorInfo;

    public SensorInfoFragment() {
    }
    final String[] typeName=new String[] {
        /* 0*/"",
        /* 1*/"Accelerometer",
        /* 2*/"Magnetic Field",
        /* 3*/"Orientation*",
        /* 4*/"Gyroscope",
        /* 5*/"Light",
        /* 6*/"Pressure",
        /* 7*/"Temperature*",
        /* 8*/"Proximity",
        /* 9*/"Gravity",
        /*10*/"Linear Acceleration",
        /*11*/"Rotation Vector",
        /*12*/"Relative Humidity",
        /*13*/"Ambient Temperature",
        /*14*/"Magnetic Field Uncalibrated",
        /*15*/"Game Rotation Vector",
        /*16*/"Gyroscope Uncalibrated",
        /*17*/"Significant Motion"
      };
    final String[] unitName=new String[] {
        /* 0*/"",
        /* 1*/"m/s^2",
        /* 2*/"\u03bcT",
        /* 3*/"\u00b0",
        /* 4*/"rad/s",
        /* 5*/"lux",
        /* 6*/"hPa",
        /* 7*/"\u00b0C",
        /* 8*/"cm",
        /* 9*/"m/s^2",
        /*10*/"m/s^2",
        /*11*/"",
        /*12*/"%",
        /*13*/"\u00b0C",
        /*14*/"\u03bcT",
        /*15*/"",
        /*16*/"rad/s",
        /*17*/"" //significant motion
      };
//    String sensorToString(Sensor I) {
//      String s="";
//      
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_sensor_info, container, false);
      mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);    
      txtSensorInfo=(TextView)rootView.findViewById(R.id.txtSensorInfo);
      List<Sensor> sensors=mSensorManager.getSensorList(Sensor.TYPE_ALL);
      String s="";
      int i=0;
      for(Sensor I:sensors) {
        s=s+String.format("%d %s\n",i,I.toString());
        i++;
      }
      txtSensorInfo.setText(s);
      return rootView;
    }

  }

  public static class SensorLogFragment extends Fragment implements OnCheckedChangeListener,SensorEventListener {
    private SensorManager mSensorManager;
    private TextView txtAccX,txtAccY,txtAccZ;
    private TextView txtBfldX,txtBfldY,txtBfldZ;
    private TextView txtGyroX,txtGyroY,txtGyroZ;
    Sensor acc,bfld,gyro;
    public SensorLogFragment() {
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
        mService.openSensor();
      } else {
      	mSensorManager.unregisterListener(this);
        mService.closeSensor();
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

  /** Defines callbacks for service binding, passed to bindService() */
  private ServiceConnection mConnection = new ServiceConnection() {

      @Override
      public void onServiceConnected(ComponentName className,
              IBinder service) {
          // We've bound to LocalService, cast the IBinder and get LocalService instance
          LocalBinder binder = (LocalBinder) service;
          mService = binder.getService();
          mBound = true;
      }

      @Override
      public void onServiceDisconnected(ComponentName arg0) {
          mBound = false;
      }
  };
}
