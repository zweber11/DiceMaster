package com.hellwebstudios.zweber.dd.DataObjects;

import android.hardware.camera2.CameraManager;

/**
 * Created by zweber on 1/4/2017.
 */
public class Adventure {

    public int AdvID;
    public String Name;
    public String Desc;
    public int CharID;
    public int NumChapters;

    //Empty
    public Adventure() {
    }

    //Loaded.
    public Adventure(int advID, String name, String desc, int charID, int numChapters) {
        AdvID = advID;
        Name = name;
        Desc = desc;
        CharID = charID;
        NumChapters = numChapters;
    }

    //Getter/setters
    public int getAdvID() {
        return AdvID;
    }

    public void setAdvID(int advID) {
//        this.AdvID = ID;
        AdvID = advID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getCharID() {
        return CharID;
    }

    public void setCharID(int charID) {
        CharID = charID;
    }

    public int getNumChapters() {
        return NumChapters;
    }

    public void setNumChapters(int numChapters) {
        NumChapters = numChapters;
    }

}
