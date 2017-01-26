package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/10/2017.
 */
public class RollAttackSet {

    //Props
    public int ID;
    public int ChapID;
    public int DSID;
    public int Initiative;
    public int RollToHit;

    //Empty
    public RollAttackSet() {
    }

    //Loaded
    public RollAttackSet(int ID, int chapID, int DSID, int initiative, int rollToHit) {
        this.ID = ID;
        ChapID = chapID;
        this.DSID = DSID;
        Initiative = initiative;
        RollToHit = rollToHit;
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

    public int getInitiative() {
        return Initiative;
    }

    public void setInitiative(int initiative) {
        Initiative = initiative;
    }

    public int getRollToHit() {
        return RollToHit;
    }

    public void setRollToHit(int rollToHit) {
        RollToHit = rollToHit;
    }
}
