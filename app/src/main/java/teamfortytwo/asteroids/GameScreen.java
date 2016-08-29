package teamfortytwo.asteroids;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class GameScreen extends Activity implements OnClickListener, SensorEventListener {
    private static final String THE_SCORE = "theScore";

    private SensorManager sManager;
    private Sensor accelerometer, magnetometer;
    boolean accSet, magSet;
    float[] lastAccelerometer = new float[3], lastMagnetometer = new float[3],
            mR = new float[9], mOrientation = new float[3];

    GameView view; //This is needed for to draw drawables

    private Button shootButton;

    static int screenWidth;
    static int screenHeight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Used for determining screen size in pixels
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(this.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        //Sensors
        sManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
        accSet = false;
        magSet = false;

        shootButton = new Button(this);
        shootButton.layout(0, 0, screenWidth, screenHeight);
        shootButton.setClickable(true);
        shootButton.setAlpha(0);
        shootButton.setOnClickListener(this);

        view = new GameView(this);

        ObjectAnimator viewAnim = ObjectAnimator.ofInt(view, "frame", 0, 60);
        viewAnim.setDuration(1000);
        viewAnim.setRepeatCount(ObjectAnimator.INFINITE);
        viewAnim.setRepeatMode(ObjectAnimator.RESTART);
        viewAnim.addUpdateListener(view);
        viewAnim.start();

        view.setOnClickListener(this);
        setContentView(view);
        System.gc();
    }

    @Override
    public void onDestroy() {
        Intent i = new Intent(GameScreen.this, ScoreScreen.class);
        i.putExtra(THE_SCORE, view.getScore());
        super.onDestroy();
        view.destroyDrawingCache();
        startActivity(i);
    }

    public void endGame() {
        finish();
        view.nullEntityArray();
        System.gc();
    }

    @Override
    protected void onResume() {
        super.onResume();
        accSet = false;
        magSet = false;
        sManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sManager.unregisterListener(this, accelerometer);
        sManager.unregisterListener(this, magnetometer);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {
            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
            accSet = true;
        } else if (event.sensor == magnetometer) {
            System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.length);
            magSet = true;
        }
        if (accSet && magSet) {
            SensorManager.getRotationMatrix(mR, null, lastAccelerometer, lastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
        }

        view.updatePlayer(mOrientation[2]);
        view.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onClick(View v) {
        view.shoot();
    }
}
