package org.kwansystems.droid.sensorlog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.kwansystems.droid.sensorlog.R;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
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
import android.widget.ToggleButton;

public class SensorLog extends FragmentActivity implements
		ActionBar.TabListener,NmeaListener {
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
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
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
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

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new GPSLogFragment();
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
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

}
