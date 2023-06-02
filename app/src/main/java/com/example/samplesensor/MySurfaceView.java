package com.example.samplesensor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, SensorEventListener {

    private static final int BALLSIZE = 30;

    private int x;
    private int y;
    private static float accel_x = 0.0f;
    private static float accel_y = 0.0f;
    private static float accel_z = 0.0f;

    private Paint paint = null;
    private Thread thread;
    private long triggerUPTime;

    public MySurfaceView(Context context) {
        super(context);

        getHolder().addCallback(this);
        paint = new Paint();
        triggerUPTime = System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accel_x = sensorEvent.values[0];
            accel_y = sensorEvent.values[1];
            accel_z = sensorEvent.values[2];

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        x = getWidth();
        y = getHeight();

        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void run() {
        while (true){

            x = x + (int) accel_x * -3;
            y = y + (int) accel_y * +3;

            if (x < BALLSIZE){
                x = BALLSIZE;
            } else if (getWidth()-BALLSIZE < x){
                x = getWidth()-BALLSIZE;
            }
            if (y < BALLSIZE){
                y = BALLSIZE;
            } else if (getWidth()-BALLSIZE < y){
                y = getWidth()-BALLSIZE;
            }

            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null){
                canvas.drawColor(Color.WHITE);
                paint.setColor(Color.RED);
                canvas.drawCircle(x, y, BALLSIZE, paint);

                paint.setColor(Color.BLACK);
                paint.setTextSize(50);

                canvas.drawText("X: " + accel_x, getWidth()/2 -200, getHeight()/2 - 50, paint);
                canvas.drawText("Y: " + accel_y, getWidth()/2 -200, getHeight()/2, paint);
                canvas.drawText("Z: " + accel_z, getWidth()/2 -200, getHeight()/2 + 50, paint);

                getHolder().unlockCanvasAndPost(canvas);
            }
        }

    }
}
