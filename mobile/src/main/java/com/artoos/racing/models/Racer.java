package com.artoos.racing.models;

/**
 * Created by Nakhimovich on 8/23/14.
 */
public class Racer
{
   public String name;
   public double speed;
   public double distance;
   public int steps;
   public int points;
   public int position;

    public Racer(String name, Double speed, Double distance, Integer steps)
    {
        this.name = name;
        this.speed = speed;
        this.distance = distance;
        this.steps = steps;
    }

    public Racer(String name)
    {
        this.name = name;
    }

    public Racer() {

    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getSpeed()
    {
        return speed;
    }

    public void setSpeed(Double speed)
    {
        this.speed = speed;
    }

    public Double getDistance()
    {
        return distance;
    }

    public void setDistance(Double distance)
    {
        this.distance = distance;
    }

    public Integer getSteps()
    {
        return steps;
    }

    public void setSteps(Integer steps)
    {
        this.steps = steps;
    }

    public Integer getPoints()
    {
        return points;
    }

    public void setPoints(Integer points)
    {
        this.points = points;
    }

    public Integer getPosition()
    {
        return position;
    }

    public void setPosition(Integer position)
    {
        this.position = position;
    }
}
