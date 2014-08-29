package com.artoos.racingrivalz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artoos.racingrivalz.models.Racer;
import com.artoos.racingrivalz.utils.DataStore;
import com.artoos.racingrivalz.utils.FirebaseHelper;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FirstActivity extends Activity
{
    @InjectView(R.id.raceText)
    TextView raceText;
    @InjectView(R.id.startRace)
    Button startRace;
    @InjectView(R.id.leaveRace)
    Button leaveRace;
    @InjectView(R.id.enterUserName)
    EditText enterUserName;
    @InjectView(R.id.userName)
    TextView userName;
    @InjectView(R.id.userNameContainer)
    LinearLayout userNameContainer;
    @InjectView(R.id.submitName)
    Button submitName;
    FirebaseHelper firebase = FirebaseHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, FireBaseService.class);
        startService(intent);
        setContentView(R.layout.race_main);
        ButterKnife.inject(this);
        firebase.seedRace();
    }

    public void onResume()
    {
        super.onResume();

        String race = DataStore.getInstance().getRace();

        if (race.length() > 0)
        {
            raceText.setText(race);
            startRace.setVisibility(View.VISIBLE);
            leaveRace.setVisibility(View.VISIBLE);
        } else {
            raceText.setText("No races! Join or make a race");
            startRace.setVisibility(View.GONE);
            leaveRace.setVisibility(View.GONE);
        }

        if (DataStore.getInstance().getRacer() != null && DataStore.getInstance().getRacer().getName() != null && DataStore.getInstance().getRacer().getName().length() > 1)
        {
            userName.setVisibility(View.VISIBLE);
            userName.setText(DataStore.getInstance().getRacer().getName());
            enterUserName.setVisibility(View.GONE);
            submitName.setVisibility(View.GONE);
        } else {
            userName.setVisibility(View.GONE);
            enterUserName.setVisibility(View.VISIBLE);
            submitName.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.newRace)
    public void newRace()
    {
        CreateRaceActivity.getLaunchIntent(this);
    }

    @OnClick(R.id.joinRace)
    public void joinRace()
    {
        if (DataStore.getInstance().getRacer()!=null)
        {
            ListActivity.getLaunchIntent(this);
        } else {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.leaveRace)
    public void leaveRace()
    {

    }

    @OnClick(R.id.submitName)
    public void submitName()
    {
        Racer racer = new Racer();
        racer.setName(enterUserName.getText().toString());
        DataStore.getInstance().setRacer(racer);

        userName.setVisibility(View.VISIBLE);
        enterUserName.setVisibility(View.GONE);
        submitName.setVisibility(View.GONE);
        userName.setText(DataStore.getInstance().getRacer().getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void timerTask()
    {
        Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask()
                              {

                                  @Override
                                  public void run()
                                  {
                                      //Called each time when 1000 milliseconds (1 second) (the period parameter)
                                  }

                              },
                //Set how long before to start calling the TimerTask (in milliseconds)
                0,
                //Set the amount of time between each execution (in milliseconds)
                10000);
    }
}
