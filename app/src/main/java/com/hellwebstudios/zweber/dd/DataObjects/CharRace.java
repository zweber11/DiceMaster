package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/1/2017.
 */
public class CharRace {

    public int RaceID;
    public String RaceName;

    //Empty constructor
    public CharRace()
    {

    }

    //Loaded constructor
    public CharRace(int raceID, String raceName) {
        RaceID = raceID;
        RaceName = raceName;
    }

    public int getRaceID() {
        return RaceID;
    }

    public void setRaceID(int raceID) {
        RaceID = raceID;
    }

    public String getRaceName() {
        return RaceName;
    }

    public void setRaceName(String raceName) {
        RaceName = raceName;
    }
}
