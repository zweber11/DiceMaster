package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/6/2017.
 */
public class Dice {

    //Props
    public int ID;
    public String Name;
    public String Description;

    //Empty
    public Dice() {
    }

    //Loaded
    public Dice(int ID, String name, String description) {
        this.ID = ID;
        Name = name;
        Description = description;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
