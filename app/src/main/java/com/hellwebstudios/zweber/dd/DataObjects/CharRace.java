package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/1/2017.
 */
public class CharRace {

    public int RaceID;
    public String RaceName;

    public CharRace() { }
    public CharRace(int raceID, String raceName) {
        RaceID = raceID;
        RaceName = raceName;
    }

    public int getRaceID() { return RaceID; }
    public String getRaceName() { return RaceName; }
}
