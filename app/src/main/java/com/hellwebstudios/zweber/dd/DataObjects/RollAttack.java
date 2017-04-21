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

    public RollAttack() { }
    public RollAttack(int ID, int RASID, int DID, int roll) {
        this.ID = ID;
        this.RASID = RASID;
        this.DID = DID;
        Roll = roll;
    }

    //Getter/setters
    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public int getDID() { return DID; }
    public int getRoll() { return Roll; }
}
