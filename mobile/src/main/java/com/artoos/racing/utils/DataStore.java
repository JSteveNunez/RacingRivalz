package com.artoos.racing.utils;

import com.artoos.racing.models.Racer;

/**
 * Created by Nakhimovich on 8/23/14.
 */
public class DataStore
{
    public Racer racer;
    public String race;



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
}
