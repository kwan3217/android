package org.kwansystems.droid.sensorlog;

import java.util.Locale;

import org.kwansystems.droid.sensorlog.R;
import org.kwansystems.droid.sensorlog.SensorLogService.LocalBinder;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

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
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
    BubbleCompassFragment bcf=new BubbleCompassFragment();
    GPSPlotFragment gpf=new GPSPlotFragment();
    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
      pageTitle=new String[] {
          getString(R.string.title_section1),
          getString(R.string.title_section2),
          getString(R.string.title_section3),
          "Bubble Compass",
          "GPS Plot"
      };
      fragment=new Fragment[] {new GPSLogFragment(gpf),new SensorInfoFragment(),new SensorLogFragment(bcf), bcf, gpf};
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
