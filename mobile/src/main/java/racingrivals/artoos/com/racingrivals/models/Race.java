package racingrivals.artoos.com.racingrivals.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nakhimovich on 8/23/14.
 */
public class Race
{
    public String name;
    public boolean isDistance;
    public double raceValue;
    public Map<String, Racer> racers = new HashMap<String, Racer>();


}
