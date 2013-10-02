package org.kwansystems.droid.sensorlog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.kwansystems.droid.sensorlog.R;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SensorLog extends FragmentActivity implements ActionBar.TabListener,NmeaListener {
  SectionsPagerAdapter mSectionsPagerAdapter;
  ViewPager mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
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
      locationManager.addNmeaListener((SensorLog)this.getActivity());
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
        txtSystemClock[i].setText(sdf.format(new Date(timestamp)));
      }
    }

    @Override
    public void onCheckedChanged(CompoundButton btnGPS, boolean isChecked) {
      if(isChecked) {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
      } else {
        locationManager.removeUpdates(this);
      }
    }
  }

  @Override
  public void onNmeaReceived(long arg0, String arg1) {
    // TODO Auto-generated method stub

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
        /* 2*/"µT",
        /* 3*/"°",
        /* 4*/"rad/s",
        /* 5*/"lux",
        /* 6*/"hPa",
        /* 7*/"°C",
        /* 8*/"cm",
        /* 9*/"m/s^2",
        /*10*/"m/s^2",
        /*11*/"",
        /*12*/"%",
        /*13*/"°C",
        /*14*/"µT",
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
      for(Sensor I:sensors) {
        s=s+I.toString()+"\n";
      }
      txtSensorInfo.setText(s);
      return rootView;
    }

  }

  public static class SensorLogFragment extends Fragment {
    private SensorManager mSensorManager;
    private TextView txtSensorInfo;

    public SensorLogFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_sensor_log, container, false);
      return rootView;
    }

  }

  public static class SensorLogService extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            long endTime = System.currentTimeMillis() + 5*1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
      // Start up the thread running the service.  Note that we create a
      // separate thread because the service normally runs in the process's
      // main thread, which we don't want to block.  We also make it
      // background priority so CPU-intensive work will not disrupt our UI.
      HandlerThread thread = new HandlerThread("ServiceStartArguments",
              Process.THREAD_PRIORITY_BACKGROUND);
      thread.start();
      
      // Get the HandlerThread's Looper and use it for our Handler 
      mServiceLooper = thread.getLooper();
      mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }
    
    @Override
    public void onDestroy() {
      Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show(); 
    }
  }
}
