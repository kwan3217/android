package org.kwansystems.musictree;

import java.io.*;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.widget.*;
import android.view.View;
import android.view.View.*;
import android.media.*;
import android.media.MediaPlayer.*;

import java.util.*;

import org.kwansystems.musictree.MusicTreeService;
import org.kwansystems.musictree.MusicTreeService.LocalBinder;

public class MusicTree extends Activity {
  TextView tv;
  ProgressBar pb;
  protected MusicTreeService mService;
  protected boolean mBound;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent=new Intent(this,MusicTreeService.class);
    startService(intent);
    bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
    setContentView(R.layout.main);
    tv = (TextView)this.findViewById(R.id.label);
    pb = (ProgressBar)this.findViewById(R.id.progress);
    pb.setProgress(50);
    pb.setMax(100);
    pb.setIndeterminate(false);
    ((Button)(this.findViewById(R.id.skipfwd)))
        .setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            mService.skipFwd();
          }
        });
    ((Button)(this.findViewById(R.id.skiprev)))
        .setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            mService.skipRev();
          }
        });
    ((Button)(this.findViewById(R.id.foldrev)))
        .setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            mService.folderRev();
          }
        });
    ((Button)(this.findViewById(R.id.foldfwd)))
        .setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            mService.folderRev();
          }
        });
    ((Button)(this.findViewById(R.id.pause)))
        .setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            mService.pausePlay();
          }
        });
  }

  /** Defines callbacks for service binding, passed to bindService() */
  private ServiceConnection mConnection = new ServiceConnection() {

//      @Override
      public void onServiceConnected(ComponentName className,
              IBinder service) {
          // We've bound to LocalService, cast the IBinder and get LocalService instance
          LocalBinder binder = (LocalBinder) service;
          mService = binder.getService();
          mBound = true;
      }

//      @Override
      public void onServiceDisconnected(ComponentName arg0) {
          mBound = false;
      }
  };

}