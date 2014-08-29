package com.artoos.racingrivalz.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nakhimovich on 8/23/14.
 */
public class Race
{
    public String name;
    public boolean isDistance;
    public long raceValue;
    public Map<String, Racer> racers = new HashMap<String, Racer>();



}
