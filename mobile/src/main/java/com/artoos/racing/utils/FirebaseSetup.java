package com.artoos.racing.utils;

import com.artoos.racing.models.Race;
import com.artoos.racing.models.Racer;
import com.artoos.racing.models.RacingRivals;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nakhimovich on 8/23/14.
 */
public class FirebaseSetup
{

    public static final String myracename = "FakeRace2";
    private Firebase baseRef;

    public void firebaseStuff()
    {
        //grab a pointer to the racers
        baseRef = DataStore.getInstance().baseRef;
        Firebase racers = baseRef.child("races").child(myracename).child("racers");


        Race race = new Race();
        race.name = myracename;
        race.isDistance = true;
        race.raceValue = 5;

        Racer mike = new Racer("mike", 20.0, 20.0, 20);
        Racer steve = new Racer("steve", 20.0, 20.0, 20);
        Racer jeff = new Racer("jeff", 20.0, 20.0, 20);
        race.racers.put("mike", mike);
        race.racers.put("steve", steve);
        race.racers.put("jeff", jeff);

        RacingRivals rivals = new RacingRivals();
        rivals.races.put(myracename, race);





        baseRef.setValue(rivals);

        //add a racer
        HashMap<String, Object> racersContainer = new HashMap<String, Object>();
        Map<String, Object> newRacer = new HashMap<String, Object>();
        newRacer.put("name", "steve2");
        racersContainer.put("steve2", newRacer);
        racers.updateChildren(racersContainer);

        Firebase currentRacerDistance = racers.child(mike.name).child("distance");
        currentRacerDistance.setValue(100);

    }

}
