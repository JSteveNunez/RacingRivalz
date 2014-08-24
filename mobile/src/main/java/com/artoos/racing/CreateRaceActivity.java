package com.artoos.racing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.artoos.racing.utils.FirebaseHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import racingrivals.artoos.com.racingrivals.R;

public class CreateRaceActivity extends Activity
{

    @InjectView(R.id.raceName)
    EditText raceName;
    @InjectView(R.id.valueLabel)
    TextView valueLabel;
    @InjectView(R.id.value)
    EditText value;
    @InjectView(R.id.distance)
    ToggleButton distance;
    @InjectView(R.id.time)
    ToggleButton time;
    @InjectView(R.id.race)
    ToggleButton race;
    @InjectView(R.id.rivalry)
    ToggleButton rivalry;
    private String name;
    private boolean isDistance;
    private long distanceOrTime;

    public static void getLaunchIntent(Context context)
    {
        Intent i = new Intent(context, CreateRaceActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.race_newrace);
        ButterKnife.inject(this);

        distance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    time.setChecked(false);
                    distance.setTextColor(getResources().getColor(R.color.white));
                    time.setTextColor(getResources().getColor(R.color.text_my_wallet));
                    isDistance = true;
                    valueLabel.setText("Distance");
                } else {
                    time.setChecked(true);
                    distance.setTextColor(getResources().getColor(R.color.text_my_wallet));
                    time.setTextColor(getResources().getColor(R.color.white));
                    isDistance = false;
                    valueLabel.setText("Time");
                }
            }
        });

        time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    distance.setChecked(false);
                    distance.setTextColor(getResources().getColor(R.color.text_my_wallet));
                    time.setTextColor(getResources().getColor(R.color.white));
                    isDistance = false;
                    valueLabel.setText("Time");
                } else {
                    distance.setChecked(true);
                    distance.setTextColor(getResources().getColor(R.color.white));
                    time.setTextColor(getResources().getColor(R.color.text_my_wallet));
                    isDistance = true;
                    valueLabel.setText("Distance");
                }
            }
        });

        race.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rivalry.setChecked(false);
                    race.setTextColor(getResources().getColor(R.color.white));
                    rivalry.setTextColor(getResources().getColor(R.color.text_my_wallet));
                } else {
                    rivalry.setChecked(true);
                    race.setTextColor(getResources().getColor(R.color.text_my_wallet));
                    rivalry.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });

        rivalry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    race.setChecked(false);
                    race.setTextColor(getResources().getColor(R.color.text_my_wallet));
                    rivalry.setTextColor(getResources().getColor(R.color.white));

                } else {
                    race.setChecked(true);
                    race.setTextColor(getResources().getColor(R.color.white));
                    rivalry.setTextColor(getResources().getColor(R.color.text_my_wallet));
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_race, menu);
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

    @OnClick(R.id.createRace)
    public void createRace()
    {
        name = raceName.getText().toString();
        String valueText = value.getText().toString();
        distanceOrTime = Long.parseLong(valueText);
        FirebaseHelper.getInstance().createNewRace(name, isDistance, distanceOrTime);
        finish();
    }


}
