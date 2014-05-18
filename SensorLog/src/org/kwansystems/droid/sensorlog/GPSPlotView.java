package org.kwansystems.droid.sensorlog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.SensorManager;
import android.location.GpsStatus.NmeaListener;
import android.util.AttributeSet;
import android.view.View;

public class GPSPlotView extends View implements NmeaListener {
  private Paint paint=new Paint();
  private float mYOffset;
  private float mScale;
  private int mWidth;
  private int mHeight;
  private int mMaxX;
  private int mLastX;
  public GPSPlotView(Context context) {
    super(context);
  }
  public GPSPlotView(Context context, AttributeSet attrs) {
    super(context,attrs);
  }
  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w,h,oldw,oldh);
    mYOffset = h * 0.5f;
    mScale = - (h * 0.5f * (1.0f / (90)));
    mWidth = w;
    mHeight = h;
    if (mWidth < mHeight) {
      mMaxX = w;
    } else {
      mMaxX = w-50;
    }
    mLastX = mMaxX;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    synchronized (this) {
      paint.setARGB(255, 0, 0, 0);
      canvas.save(Canvas.MATRIX_SAVE_FLAG); 
      canvas.translate(mWidth/2, mHeight/2);
      canvas.drawCircle(0, 0, mScale, paint);
      canvas.restore();
    }
  }
  @Override
  public void onNmeaReceived(long timestamp, String nmea) {
    // TODO Auto-generated method stub
    invalidate(); 
  }
}
