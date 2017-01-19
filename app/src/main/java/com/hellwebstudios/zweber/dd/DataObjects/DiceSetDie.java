package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/6/2017.
 */
public class DiceSetDie {

    //Props
    public int ID;
    public int DiceSetID;
    public int DiceID;

    //Empty
    public DiceSetDie() {
    }

    //Loaded
    public DiceSetDie(int ID, int diceSetID, int diceID) {
        this.ID = ID;
        DiceSetID = diceSetID;
        DiceID = diceID;
    }

    //Getter/setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDiceSetID() {
        return DiceSetID;
    }

    public void setDiceSetID(int diceSetID) {
        DiceSetID = diceSetID;
    }

    public int getDiceID() {
        return DiceID;
    }

    public void setDiceID(int diceID) {
        DiceID = diceID;
    }
}
