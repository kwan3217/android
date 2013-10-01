package org.kwansystems.musictree;

import java.io.*;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.*;
import android.view.View;
import android.view.View.*;
import android.media.*;
import android.media.MediaPlayer.*;

import java.util.*;

public class MusicTree extends Activity implements OnCompletionListener {
  List<File> list;
  int index;
  MediaPlayer mp;
  TextView tv;
  ProgressBar pb;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    tv = (TextView)this.findViewById(R.id.label);
    pb = (ProgressBar)this.findViewById(R.id.progress);
    pb.setProgress(50);
    pb.setMax(100);
    pb.setIndeterminate(false);
    mp = new MediaPlayer();
    mp.setOnCompletionListener(this);
    ((Button)(this.findViewById(R.id.skipfwd)))
        .setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            if (index < list.size() - 1) {
              index++;
              start();
            }
          }
        });
    ((Button)(this.findViewById(R.id.skiprev)))
        .setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            if (index > 0) {
              index--;
              start();
            }
          }
        });
    ((Button)(this.findViewById(R.id.foldrev)))
        .setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
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
        });
    ((Button)(this.findViewById(R.id.foldfwd)))
        .setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            String currfolder = list.get(index).getParent();
            while (index < list.size() - 1
                && list.get(index).getParent().equals(currfolder)) {
              index++;
            }
            start();
          }
        });
    ((Button)(this.findViewById(R.id.pause)))
        .setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            if (mp.isPlaying()) {
              mp.pause();
            } else {
              mp.start();
            }
          }
        });
    try {
      list = findSongs(new File(Environment.getExternalStorageDirectory().getPath()+"/Music"));
      index = 0;
      start();
    } catch (IOException e) {
      Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT)
          .show();
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
      tv.setText(s);
      mp.reset();
      mp.setDataSource(s);
      mp.prepare();
      pb.setMax(mp.getDuration());
    } catch (IllegalArgumentException e) {
      Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT)
          .show();
    } catch (IllegalStateException e) {
      Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT)
          .show();
    } catch (IOException e) {
      Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT)
          .show();
    }
    mp.start();
  }

  public void onCompletion(MediaPlayer mpp) {
    if (index < list.size() - 1) {
      index++;
      start();
    }
  }
}