package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/4/2017.
 */
public class Chapter {

    //Props
    public int ChapID;
    public int AdvID;
    public String Name;
    public int RollToHit;

    //Empty
    public Chapter() {
    }

    //Loaded
    public Chapter(int chapID, int advID, String name, int rollToHit) {
        ChapID = chapID;
        AdvID = advID;
        Name = name;
        RollToHit = rollToHit;
    }

    //Getter/setters
    public int getChapID() {
        return ChapID;
    }

    public void setChapID(int chapID) {
        ChapID = chapID;
    }

    public int getAdvID() {
        return AdvID;
    }

    public void setAdvID(int advID) {
        AdvID = advID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getRollToHit() {
        return RollToHit;
    }

    public void setRollToHit(int rollToHit) {
        RollToHit = rollToHit;
    }
}
