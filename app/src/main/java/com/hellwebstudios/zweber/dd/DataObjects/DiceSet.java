package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 12/28/2016.
 */
public class DiceSet {

    //Props
    public int ID;
    public String Name;
    public int CharID;

    //Empty
    public DiceSet() {
    }

    //Loaded
    public DiceSet(int ID, String name, int charID) {
        this.ID = ID;
        Name = name;
        CharID = charID;
    }

    //Getter/setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCharID() {
        return CharID;
    }

    public void setCharID(int charID) {
        CharID = charID;
    }

}
