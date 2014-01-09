package org.kwansystems.musictree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.app.*;
import android.content.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.*;
import android.location.GpsStatus.NmeaListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.*;
import android.widget.*;

public class MusicTreeService extends Service implements OnCompletionListener {
  private List<File> list;
  private int index;
  private MediaPlayer mp;
  @Override
  public void onCreate() {

  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Toast.makeText(this, "MusicTree service starting", Toast.LENGTH_SHORT).show();
    mp = new MediaPlayer();
    mp.setOnCompletionListener(this);
    try {
      list = findSongs(new File(Environment.getExternalStorageDirectory().getPath()+"/Music"));
      index = 0;
      start();
    } catch (IOException e) {
      Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT)
          .show();
    }

    // If we get killed, after returning from here, restart
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    Toast.makeText(this, "MusicTree service done", Toast.LENGTH_SHORT).show();
  }

  // Binder given to clients
  private final IBinder mBinder = new LocalBinder();

  /**
   * Class used for the client Binder. Because we know this service always runs
   * in the same process as its clients, we don't need to deal with IPC.
   */
  public class LocalBinder extends Binder {
    MusicTreeService getService() {
      // Return this instance of LocalService so clients can call public
      // methods
      return MusicTreeService.this;
    }
  }

  @Override
  public IBinder onBind(Intent intent) {
    return mBinder;
  }

  /** method for clients */
  public void skipFwd() {
    if (index < list.size() - 1) {
      index++;
      start();
    }
  }
  public void skipRev() {
    if (index > 0) {
      index--;
      start();
    }
  }
  public void folderRev() {
    String currfolder = list.get(index).getParent();
    while (index > 0 && list.get(index).getParent().equals(currfolder)) {
      index--;
    }
    currfolder = list.get(index).getParent();
    while (index > 0 && list.get(index).getParent().equals(currfolder)) {
      index--;
    }
    index++;
    start();
  }
  public void folderFwd() {
    String currfolder = list.get(index).getParent();
    while (index < list.size() - 1
        && list.get(index).getParent().equals(currfolder)) {
      index++;
    }
    start();
  }
  public void pausePlay() {
    if (mp.isPlaying()) {
      mp.pause();
    } else {
      mp.start();
    }
  }
  public List<File> findSongs(File root) throws IOException {
    List<File> result = new ArrayList<File>();
    File[] files = root.listFiles();
    for (File file : files) {
      if (file.isDirectory()) {
        result.addAll(findSongs(file));
      } else if (file.getName().endsWith("mp3")
          || file.getName().endsWith("ogg")) {
        result.add(file.getCanonicalFile());
      }
    }
    Collections.sort(result);
    return result;
  }

  public void start() {
    try {
      String s = list.get(index).getCanonicalPath();
//      tv.setText(s);
      mp.reset();
      mp.setDataSource(s);
      mp.prepare();
//      pb.setMax(mp.getDuration());
    } catch (IllegalArgumentException e) {
      Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
    } catch (IllegalStateException e) {
      Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
    } catch (IOException e) {
      Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
    }
    mp.start();
  }

  public void onCompletion(MediaPlayer mpp) {
    skipFwd();
  }
}
