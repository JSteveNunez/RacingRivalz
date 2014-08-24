package com.artoos.racing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.artoos.racing.models.Racer;
import com.artoos.racing.utils.DataStore;
import com.artoos.racing.utils.FirebaseHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import racingrivals.artoos.com.racingrivals.R;


public class FirstActivity extends Activity
{
    @InjectView(R.id.raceText)
    TextView raceText;
    @InjectView(R.id.startRace)
    Button startRace;
    @InjectView(R.id.leaveRace)
    Button leaveRace;
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
        String race = DataStore.getInstance().getRace();

        if (race.length() > 0)
        {
            raceText.setText(race);
        } else {
            raceText.setText("No races! Join or make a race");
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
        //TODO: move to another screen
        DataStore.getInstance().setRacer(new Racer("Mike"));
        ListActivity.getLaunchIntent(this);
    }

    @OnClick(R.id.leaveRace)
    public void leaveRace()
    {

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
}
