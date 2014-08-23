package racingrivals.artoos.com.racingrivals;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Wearable;

public class MyActivity extends Activity implements SensorEventListener{

    TextView mTextView;
    SensorManager sensorManager;
    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        sensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mTextView.setText("waiting");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Sensor stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    //*************************
    //SensorEventListener
    //*************************
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_STEP_COUNTER) {
            if(mTextView!=null)
            mTextView.setText(""+sensorEvent.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
