package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/10/2017.
 */
public class RollAttackSet {

    //Props
    public int ID;
    public int ChapID;
    public int DSID;

    //Empty
    public RollAttackSet() {
    }

    //Loaded
    public RollAttackSet(int ID, int chapID, int DSID) {
        this.ID = ID;
        ChapID = chapID;
        this.DSID = DSID;
    }

    //Getter/setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getChapID() {
        return ChapID;
    }

    public void setChapID(int chapID) {
        ChapID = chapID;
    }

    public int getDSID() {
        return DSID;
    }

    public void setDSID(int DSID) {
        this.DSID = DSID;
    }
}
