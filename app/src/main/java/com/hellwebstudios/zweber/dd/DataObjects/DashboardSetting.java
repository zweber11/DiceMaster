package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 4/19/2017.
 */

public class DashboardSetting {

    //Props
    public int ID;
    public String Name;
    public String Description;

    //Empty
    public DashboardSetting() { }

    //Loaded
    public DashboardSetting(int ID, String name, String description) {
        this.ID = ID;
        Name = name;
        Description = description;
    }

    //Getters/Setters
    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public String getName() { return Name; }
    public void setName(String name) { Name = name; }

    public String getDescription() { return Description; }
    public void setDescription(String description) { Description = description; }

}
