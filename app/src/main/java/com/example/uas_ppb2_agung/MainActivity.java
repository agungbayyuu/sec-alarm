package com.example.uas_ppb2_agung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView x, y, z, hasilMotion, hasilProximity, hasilFinger;
    float x1, y1, z1;
    MediaPlayer player;
    TextView proximitysensor, data;
    SensorManager MySensorManager;
    Sensor MyProximitySensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x = findViewById(R.id.dataX);
        y = findViewById(R.id.dataY);
        z = findViewById(R.id.dataZ);
        hasilMotion = findViewById(R.id.hasilMotion);
        hasilProximity = findViewById(R.id.hasilProximity);
        hasilFinger = findViewById(R.id.hasilFingerprint);
        data = findViewById(R.id.dataProximity);
        LinearLayout banner4 = (LinearLayout) findViewById(R.id.banner4);

        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            // this means we can use biometric sensor
            case BiometricManager.BIOMETRIC_SUCCESS:
                break;

            // this means that the device doesn't have fingerprint sensor
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                break;

            // this means that biometric sensor is not available
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                break;

            // this means that the device doesn't contain your fingerprint
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                break;
        }
        // creating a variable for our Executor
        Executor executor = ContextCompat.getMainExecutor(this);
        // this will give us result of AUTHENTICATION
        final BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            // THIS METHOD IS CALLED WHEN AUTHENTICATION IS SUCCESS
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Alarm is Off", Toast.LENGTH_SHORT).show();
                player.pause();
                hasilMotion.setText(null);
                hasilProximity.setText(null);
                hasilFinger.setText(null);
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        // creating a variable for our promptInfo
        // BIOMETRIC DIALOG
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("GFG")
                .setDescription("Use your fingerprint to login ").setNegativeButtonText("Cancel").build();
        banner4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // menambahkan listener.
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),

                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),

                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] == 0) {
                data.setText("Sensor Covered");
                hasilProximity.setText("The sensor is covered by something");
                hasilFinger.setText("Alarm is on, stop alarm using fingerprint");

                playSound(1);
            } else {
                data.setText("Sensor is not covered");
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // assign directions
            x1 = event.values[0];
            y1 = event.values[1];
            z1 = event.values[2];

            x.setText("X: " + x1);
            y.setText("Y: " + y1);
            z.setText("Z: " + z1);

            if (x1 >= 7.0 || y1 >= 7.0) {
                hasilMotion.setText("Your phone moves significantly");
                playSound(1);
                hasilFinger.setText("Alarm is on, stop alarm using fingerprint");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
        private void playSound(int arg) {
            try {
                if (player == null) {
                    player = MediaPlayer.create(MainActivity.this, R.raw.alarm);
                }
                if (arg == 1) {

//                if (!player.isPlaying()) {
                    //player.stop();
                    //player.release();
                    player.setLooping(false); // Set looping
                    player.start();
//                }
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, " Masuk Exception", Toast.LENGTH_LONG).show();
            }
        }
    }

