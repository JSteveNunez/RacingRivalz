package com.artoos.racing;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.artoos.racing.utils.DataStore;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FireBaseService extends Service {


    @Override
    public void onCreate()
    {
        super.onCreate();
        //  String json = jsonParser.convertObjectToJSON(rivals);
        DataStore.getInstance().getBaseRef().addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                // do some stuff once
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
