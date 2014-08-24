package com.artoos.racing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.artoos.racing.models.Race;
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
                HashMap dataMap = (HashMap) snapshot.getValue();
                HashMap map= (HashMap) dataMap;
                ArrayList<String> raceNames = new ArrayList<String>(map.keySet());
                List<Race> list = new ArrayList<Race>();
                for (String raceName : raceNames) {
                    HashMap raceHash = (HashMap) dataMap.get(raceName);
                    Race race = new Race();
                    race.name = (String) raceHash.get("name");
                    if(raceHash.get("isDistance").equals("true")){
                        race.isDistance = true;
                    }else{
                        race.isDistance = false;
                    }
                    race.raceValue = (Long) raceHash.get("raceValue");
                    list.add(race);
                }


                CustomArrayAdapter adapter = new CustomArrayAdapter(context, list);


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
                String raceName = ((Race)listView.getItemAtPosition(position)).name;

//                // Show Alert
//                Toast.makeText(getApplicationContext(),
//                        "Position :" + itemPosition + "  ListItem : " + raceName, Toast.LENGTH_LONG)
//                        .show();
                DataStore.getInstance().setRace(raceName);
                FirebaseHelper.getInstance().addRacer();
                finish();

            }

        });
    }

    public class CustomArrayAdapter extends BaseAdapter
    {
        private final Context context;
        private  List<Race> list;

        public CustomArrayAdapter(Context context, List<Race> list){
            super();
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount(){
            return list.size();
        }

        @Override
        public Race getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Race currentRace = list.get(position);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.race, parent, false);

            TextView textViewName = (TextView) rowView.findViewById(R.id.raceName);
            textViewName.setText(currentRace.name);

            TextView textViewType = (TextView) rowView.findViewById(R.id.raceType);
            if(currentRace.isDistance == true){
                textViewType.setText("Distance");
            }else{
                textViewType.setText("Time");
            }

            TextView textViewValue = (TextView) rowView.findViewById(R.id.raceValue);
            textViewValue.setText(String.valueOf(currentRace.raceValue));

            return rowView;
        }

//        public void setData(List<Comment> commentsMade){
//            this.comments = commentsMade;
//            notifyDataSetChanged();
//        }
    }

}