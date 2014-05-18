package org.kwansystems.navcalc;

import java.util.*;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.*;
import android.text.InputType;
import android.view.View;

public class NavCalcActivity extends Activity {
  private static String pad(int c) {
    if (c >= 10) {
      return String.valueOf(c);
    } else {
      return "0" + String.valueOf(c);
    }
  }  
  private class NavDoodad implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    public int id;
    private int hour, minute;
    private TextView mDisplay;
    private NavCalcActivity parent;
    
    public NavDoodad(int Lid, TextView LDisplay, NavCalcActivity Lparent) {
      mDisplay=LDisplay;
      id=Lid;
      hour=-1;
      minute=-1;
      parent=Lparent;
    }
    
    public int getMOD() {
      return hour*60+minute;
    }
    public int getHour() {
      return hour;
    };
    public int getMinute() {
      return minute;
    };
    public void setHour(int Lhour) {
      hour=Lhour;
      parent.recalc();
    }
    public void setMinute(int Lminute) {
      minute=Lminute;
      updateDisplay();
      parent.recalc();
    }
    public void setToNow() {
      Calendar C=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
      setHour(C.get(Calendar.HOUR_OF_DAY));
      setMinute(C.get(Calendar.MINUTE));
    }
    public void updateDisplay() {
      if(hour<0) {
        mDisplay.setText("-----");
      } else {
        mDisplay.setText(pad(hour)+pad(minute)+"Z");
      }
    }
    public void onTimeSet(TimePicker view, int Lhour, int Lminute) {
      setHour(Lhour);
      setMinute(Lminute);
    }
    public void onClick(View v) {
      showDialog(id);
    }
  }
  private NavDoodad[] NavDoodads;
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);

      // add a click listener to the button
      NavDoodads=new NavDoodad[2];
      NavDoodads[0]=new NavDoodad(0,(TextView) findViewById(R.id.TakeoffTime),this);
      NavDoodads[1]=new NavDoodad(1,(TextView) findViewById(R.id.LandingTime),this);
      if(savedInstanceState!=null) {
        NavDoodads[0].setHour  (savedInstanceState.getInt("N0h"));
        NavDoodads[0].setMinute(savedInstanceState.getInt("N0m"));
        NavDoodads[1].setHour  (savedInstanceState.getInt("N1h"));
        NavDoodads[1].setMinute(savedInstanceState.getInt("N1m"));
      }
      ((Button) findViewById(R.id.TakeoffSet)).setOnClickListener(NavDoodads[0]);
      ((Button) findViewById(R.id.LandingSet)).setOnClickListener(NavDoodads[1]);
      ((Button) findViewById(R.id.TakeoffNow)).setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          NavDoodads[0].setToNow(); 
        }
      });
      ((Button) findViewById(R.id.LandingNow)).setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          NavDoodads[1].setToNow();       
        }
      });
      ((Button) findViewById(R.id.TaxiSet)).setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          recalc();       
        }
      });
      ((Button) findViewById(R.id.Victory)).setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          EditText TaxiTime=(EditText) findViewById(R.id.TaxiTime);
          if(TaxiTime.getText().toString().equals("3217")) {
            startActivity(new Intent(getApplicationContext(), TwofishActivity.class));                   
          } else {
            recalc();
            MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.victory);
            mediaPlayer.start(); // no need to call prepare(); create() does that for you
          }
        }
      });
    }
    protected void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);
      outState.putInt("N0h",NavDoodads[0].getHour()  );
      outState.putInt("N0m",NavDoodads[0].getMinute());
      outState.putInt("N1h",NavDoodads[1].getHour()  );
      outState.putInt("N1m",NavDoodads[1].getMinute());
    }
    static int[] AirForceFraction=new int[60];
    static int[] AirForceFractionStart=new int[] {3,9,15,21,27,34,40,46,52,58,60};
    static {
      int j=0;
      for(int i=0;i<60;i++) {
      if(AirForceFractionStart[j]==i) j++;
      AirForceFraction[i]=j;
      }
    }
    protected void recalc() {
      TextView SortieTime=(TextView) findViewById(R.id.SortieDuration);
      TextView BlockTime=(TextView) findViewById(R.id.BlockTime);
      EditText TaxiTime=(EditText) findViewById(R.id.TaxiTime);
      String S=TaxiTime.getText().toString();
      int tt;
      try {
        tt=Integer.parseInt(S);
      } catch (NumberFormatException e) {tt=0;}
      int m0=NavDoodads[0].getMOD();
      int m1=NavDoodads[1].getMOD();
      if(m1<m0) m1+=1440;
      int m=m1-m0+tt;
      int sortie=(m/60)*10+AirForceFraction[m%60];
      SortieTime.setText(Integer.toString(sortie/10)+"."+Integer.toString(sortie%10));
      int hblock,mblock,modblock;
      modblock=m1+tt;
      hblock=modblock/60;
      mblock=modblock%60;
      hblock%=24;
      BlockTime.setText(pad(hblock)+pad(mblock)+"Z");
    }

    @Override
    protected Dialog onCreateDialog(int id) {
      int h,m;
      if(NavDoodads[id].hour<0) {
      Calendar C=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
      h=C.get(Calendar.HOUR_OF_DAY);
      m=C.get(Calendar.MINUTE);
      } else {
      h=NavDoodads[id].getHour();
      m=NavDoodads[id].getMinute();
      }
      return new TimePickerDialog(this, NavDoodads[id], h, m, true);
    }        
}
