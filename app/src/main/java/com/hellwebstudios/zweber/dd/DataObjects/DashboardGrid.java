package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 4/19/2017.
 */

public class DashboardGrid {

    //Props
    public int ID;
    public String Position;
    public int DSID;
    public String TileColor;
    public String TileTextColor;

    //Empty
    public DashboardGrid() { }

    //Loaded
    public DashboardGrid(int ID, String position, int DSID, String tileColor, String tileTextColor) {
        this.ID = ID;
        Position = position;
        this.DSID = DSID;
        TileColor = tileColor;
        TileTextColor = tileTextColor;
    }

    //Getter/Setters
    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public String getPosition() { return Position; }
    public void setPosition(String position) { Position = position; }

    public int getDSID() { return DSID; }
    public void setDSID(int DSID) { this.DSID = DSID; }

    public String getTileColor() { return TileColor; }
    public void setTileColor(String tileColor) { TileColor = tileColor; }

    public String getTileTextColor() { return TileTextColor; }
    public void setTileTextColor(String tileTextColor) { TileTextColor = tileTextColor; }

}
