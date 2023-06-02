package com.example.samplesensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager manager;
    private Sensor accele;
    private MySurfaceView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        view = new MySurfaceView(this);
        setContentView(view);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> list = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (list.size() > 0){
            accele = list.get(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(view, accele, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(view);
    }
}