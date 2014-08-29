package com.artoos.racingrivalz;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.artoos.racingrivalz.models.Racer;
import com.artoos.racingrivalz.utils.DataStore;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FireBaseService extends Service {


    @Override
    public void onCreate()
    {
        super.onCreate();
        DataStore.getInstance().getRacesRef().addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                if (DataStore.getInstance().getRace().equals(""))
                {
                    return;
                }
                Map finalRacers = new HashMap<String, Racer>();
                HashMap races = (HashMap) snapshot.getValue();

                HashMap race = (HashMap) races.get(DataStore.getInstance().getRace());

                HashMap racers = (HashMap) race.get("racers");
                if (racers == null)
                {
                    return;


                }
                for (Object racerName : racers.keySet())
                {
                    String currentRacerName = (String) racerName;
                    Map currentRacerRecord = (HashMap) racers.get(racerName);
                    finalRacers.put(currentRacerName, currentRacerRecord);
                }
                //TODO:update watch display
              //  finalRacers
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
