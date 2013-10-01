package org.kwansystems.droid.gpslog;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.location.*;
import static android.location.GpsStatus.*;
import android.content.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.kwansystems.droid.gpslog.R;

public class GPSLog extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_log);
    	// Acquire a reference to the system Location Manager
    	LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		    final TextView[] txtSystemClock=new TextView[] {
		    		(TextView)findViewById(R.id.txtSystemClock00),
		    		(TextView)findViewById(R.id.txtSystemClock01),
		    		(TextView)findViewById(R.id.txtSystemClock02),
		    		(TextView)findViewById(R.id.txtSystemClock03),
		    		(TextView)findViewById(R.id.txtSystemClock04),
		    		(TextView)findViewById(R.id.txtSystemClock05),
		    		(TextView)findViewById(R.id.txtSystemClock06),
		    		(TextView)findViewById(R.id.txtSystemClock07),
		    		(TextView)findViewById(R.id.txtSystemClock08),
		    		(TextView)findViewById(R.id.txtSystemClock09),
		    		(TextView)findViewById(R.id.txtSystemClock10),
		    		(TextView)findViewById(R.id.txtSystemClock11),
		    		(TextView)findViewById(R.id.txtSystemClock12),
		    		(TextView)findViewById(R.id.txtSystemClock13),
		    		(TextView)findViewById(R.id.txtSystemClock14),
		    		(TextView)findViewById(R.id.txtSystemClock15),
		    		(TextView)findViewById(R.id.txtSystemClock16),
		    		(TextView)findViewById(R.id.txtSystemClock17),
		    		(TextView)findViewById(R.id.txtSystemClock18),
		    		(TextView)findViewById(R.id.txtSystemClock19)
		    };
		    final TextView[] txtNMEA=new TextView[] {
		    		(TextView)findViewById(R.id.txtNMEA00),		
		    		(TextView)findViewById(R.id.txtNMEA01),		
		    		(TextView)findViewById(R.id.txtNMEA02),		
		    		(TextView)findViewById(R.id.txtNMEA03),		
		    		(TextView)findViewById(R.id.txtNMEA04),		
		    		(TextView)findViewById(R.id.txtNMEA05),		
		    		(TextView)findViewById(R.id.txtNMEA06),		
		    		(TextView)findViewById(R.id.txtNMEA07),		
		    		(TextView)findViewById(R.id.txtNMEA08),		
		    		(TextView)findViewById(R.id.txtNMEA09),		
		    		(TextView)findViewById(R.id.txtNMEA10),		
		    		(TextView)findViewById(R.id.txtNMEA11),		
		    		(TextView)findViewById(R.id.txtNMEA12),		
		    		(TextView)findViewById(R.id.txtNMEA13),		
		    		(TextView)findViewById(R.id.txtNMEA14),		
		    		(TextView)findViewById(R.id.txtNMEA15),		
		    		(TextView)findViewById(R.id.txtNMEA16),		
		    		(TextView)findViewById(R.id.txtNMEA17),		
		    		(TextView)findViewById(R.id.txtNMEA18),		
		    		(TextView)findViewById(R.id.txtNMEA19),		
		    };
            final SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss.SSS");
            final ArrayList<String> l=new ArrayList<String>();
    	//Looks like you gotta do this to turn on the GPS
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,new LocationListener(){
			@Override
			public void onLocationChanged(Location loc) {}
			@Override
			public void onProviderDisabled(String provider) {}
			@Override
			public void onProviderEnabled(String provider) {}
			@Override
			public void onStatusChanged(String provider, int status,Bundle extras) {} 
	      }
        );
    	// Register the listener with the Location Manager to receive location updates
    	locationManager.addNmeaListener(new NmeaListener() {

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
        	}
    	);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gps_log, menu);
        return true;
    }
    
}
