package racingrivals.artoos.com.racingrivals.utils;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import racingrivals.artoos.com.racingrivals.models.Race;
import racingrivals.artoos.com.racingrivals.models.Racer;
import racingrivals.artoos.com.racingrivals.models.RacingRivals;

/**
 * Created by Nakhimovich on 8/23/14.
 */
public class FirebaseSetup
{

    public static final String myracename = "FakeRace2";

    public void firebaseStuff()
    {
        Racer mike = new Racer();
        mike.name = "Mike";
        mike.distance = 5.3;
        mike.speed = 20.5;
        mike.steps = 15;

        Racer steve = new Racer();
        steve.name = "Steve";
        steve.distance = 5.3;
        steve.speed = 20.5;
        steve.steps = 15;

        Racer jeff = new Racer();
        jeff.name = "Jeff";
        jeff.distance = 5.3;
        jeff.speed = 20.5;
        jeff.steps = 15;

        Race race = new Race();
        race.name = myracename;
        race.isDistance = true;
        race.raceValue = 5;
        race.racers.put("mike", mike);
        race.racers.put("steve", steve);
        race.racers.put("jeff", jeff);

        RacingRivals rivals = new RacingRivals();
        rivals.races.put(myracename, race);


        //  String json = jsonParser.convertObjectToJSON(rivals);
        com.firebase.client.Firebase ref = new com.firebase.client.Firebase("https://blistering-fire-2373.firebaseio.com/");
        com.firebase.client.Firebase rivalRef = ref.child("rival");
        rivalRef.setValue(rivals);
        com.firebase.client.Firebase racers = rivalRef.child("races").child(myracename).child("racers");


        HashMap<String, Object> racersContainer = new HashMap<String, Object>();

        Map<String, Object> newRacer = new HashMap<String, Object>();
        newRacer.put("name", "steve2");


        racersContainer.put("steve2", newRacer);

        racers.updateChildren(racersContainer);


        rivalRef.addValueEventListener(new ValueEventListener()
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


    //Firebase updateCurrentStep=rivalRef.child(myracename).child(jeff.name).child("steps");

}
