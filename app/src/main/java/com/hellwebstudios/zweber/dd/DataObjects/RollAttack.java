package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/9/2017.
 */
public class RollAttack {

    //Props
    public int ID;
    public int RASID;
    public int DID;
    public int Roll;

    //Empty
    public RollAttack() {
    }

    //Loaded
    public RollAttack(int ID, int RASID, int DID, int roll) {
        this.ID = ID;
        this.RASID = RASID;
        this.DID = DID;
        Roll = roll;
    }

    //Getter/setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRASID() {
        return RASID;
    }

    public void setRASID(int RASID) {
        this.RASID = RASID;
    }

    public int getDID() {
        return DID;
    }

    public void setDID(int DID) {
        this.DID = DID;
    }

    public int getRoll() {
        return Roll;
    }

    public void setRoll(int roll) {
        Roll = roll;
    }
}
