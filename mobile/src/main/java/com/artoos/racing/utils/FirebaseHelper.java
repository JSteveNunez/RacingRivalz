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
public class FirebaseHelper
{

    private Firebase baseRef;
    private DataStore dataStore = DataStore.getInstance();
    ;
    Firebase races;
    Firebase racers;
    private Firebase currentRace;


    private static FirebaseHelper ourInstance = new FirebaseHelper();
    public static FirebaseHelper getInstance()
    {
        return ourInstance;
    }




    private FirebaseHelper()
    {

    }


    public void seedRace()
    {
        initRefs();
        // Race race = createRaceAndRacers();

        //add a racer
        //addRacer(racers);

        // String fieldToUpdate = "distance";
        //updateRacerField(racers, fieldToUpdate);

        addRace();

    }

    private Race createRaceAndRacers()
    {
        Race race = new Race();
        race.name = dataStore.getRace();
        race.isDistance = true;
        race.raceValue = 5;

        Racer racer = new Racer("mike", 20.0, 20.0, 20);
        DataStore.getInstance().setRacer(racer);
        race.racers.put("mike", racer);
        racer = new Racer("steve", 20.0, 20.0, 20);
        race.racers.put("steve", racer);
        racer = new Racer("jeff", 20.0, 20.0, 20);
        race.racers.put("jeff", racer);

        RacingRivals rivals = new RacingRivals();
        rivals.races.put(dataStore.getRace(), race);

        baseRef.setValue(rivals);
        return race;
    }

    private void initRefs()
    {
        baseRef = dataStore.baseRef;
        races = baseRef.child("races");
        currentRace = races.child(dataStore.getRace());
        racers = currentRace.child("racers");
    }

    private void updateRacerField(Firebase racers, String fieldToUpdate)
    {
        Firebase currentRacerDistance = racers.child(dataStore.getRacer().name).child(fieldToUpdate);
        currentRacerDistance.setValue(100);
    }

    private void addRace()
    {
        HashMap<String, Object> racesContainer = new HashMap<String, Object>();
        Map<String, Object> newRace = new HashMap<String, Object>();
        String raceName = "secondRace";
        newRace.put("name", raceName);
        newRace.put("isDistance", true);
        newRace.put("raceValue", 5);
        racesContainer.put(raceName, newRace);
        races.updateChildren(racesContainer);
        dataStore.setRace(raceName);
        currentRace = races.child(dataStore.getRace());
        racers = currentRace.child("racers");

    }

    public void addRacer()
    {
        currentRace = races.child(dataStore.getRace());
        HashMap<String, Object> racersContainer = new HashMap<String, Object>();
        Map<String, Object> newRacer = new HashMap<String, Object>();
        newRacer.put("name", dataStore.getRacer().name);
        racersContainer.put(dataStore.getRacer().name, newRacer);
        racers.updateChildren(racersContainer);
    }

    public void createNewRace(String raceName, Boolean isDistance, Double raceValue)
    {
        HashMap<String, Object> racesContainer = new HashMap<String, Object>();
        Map<String, Object> newRace = new HashMap<String, Object>();
        newRace.put("name", raceName);
        newRace.put("isDistance", isDistance);
        newRace.put("raceValue", raceValue);
        racesContainer.put(raceName, newRace);
        races.updateChildren(racesContainer);
        dataStore.setRace(raceName);
        currentRace = races.child(dataStore.getRace());
        racers = currentRace.child("racers");
    }
}
