package com.artoos.racing.utils;

import com.artoos.racing.models.Racer;
import com.firebase.client.Firebase;

/**
 * Created by Nakhimovich on 8/23/14.
 */
public class DataStore
{
    public Racer racer;
    public String race;
    Firebase baseRef = new com.firebase.client.Firebase("https://blistering-fire-2373.firebaseio.com/");



    private static DataStore ourInstance = new DataStore();
    public static DataStore getInstance()
    {
        return ourInstance;
    }




    private DataStore()
    {

    }

    public Racer getRacer()
    {
        return racer;
    }

    public void setRacer(Racer racer)
    {
        this.racer = racer;
    }

    public String getRace()
    {
        return race;
    }

    public void setRace(String race)
    {
        this.race = race;
    }

    public Firebase getBaseRef()
    {
        return baseRef;
    }
}