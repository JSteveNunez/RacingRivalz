package com.artoos.racing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.artoos.racing.utils.DataStore;
import com.artoos.racing.utils.FirebaseHelper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import racingrivals.artoos.com.racingrivals.R;

public class ListActivity extends Activity
{
    ListView listView;
    Context context=this;
    public static void getLaunchIntent(Context context)
    {
        Intent i = new Intent(context, ListActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);


        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        DataStore.getInstance().getRacesRef().addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                List<String> list = new ArrayList<String>(((HashMap)snapshot.getValue()).keySet());


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, android.R.id.text1, list);


                // Assign adapter to ListView
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
            }
        });

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String raceName = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + raceName, Toast.LENGTH_LONG)
                        .show();
                DataStore.getInstance().setRace(raceName);
                FirebaseHelper.getInstance().addRacer();
                finish();

            }

        });
    }

}