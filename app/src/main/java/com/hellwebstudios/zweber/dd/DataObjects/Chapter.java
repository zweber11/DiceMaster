package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/4/2017.
 */
public class Chapter {

    public int ChapID;
    public int AdvID;
    public String Name;

    //Empty
    public Chapter() {
    }

    //Loaded
    public Chapter(int chapID, int advID, String name) {
        ChapID = chapID;
        AdvID = advID;
        Name = name;
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
}
