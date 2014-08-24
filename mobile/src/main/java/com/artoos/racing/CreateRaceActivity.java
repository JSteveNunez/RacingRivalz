package com.artoos.racing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.artoos.racing.utils.FirebaseHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import racingrivals.artoos.com.racingrivals.R;

public class CreateRaceActivity extends Activity
{

    @InjectView(R.id.textView)
    TextView mTextView;
    @InjectView(R.id.editText)
    EditText mEditText;
    @InjectView(R.id.radioButton)
    RadioButton mRadioButton;
    @InjectView(R.id.radioButton2)
    RadioButton mRadioButton2;
    @InjectView(R.id.radioButton3)
    RadioButton mRadioButton3;
    @InjectView(R.id.radioButton4)
    RadioButton mRadioButton4;

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
        FirebaseHelper.getInstance().createNewRace("Mike's Race", true, 5);
        finish();
    }


}
