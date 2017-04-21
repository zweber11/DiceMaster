package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/8/2017.
 */
public class RollSkill {

    //Props
    public int ID;
    public int SkillID;
    public int ChapID;
    public int Roll;

    public RollSkill() { }
    public RollSkill(int ID, int skillID, int chapID, int roll) {
        this.ID = ID;
        SkillID = skillID;
        ChapID = chapID;
        Roll = roll;
    }

    //Getter/setters
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getSkillID() {
        return SkillID;
    }
    public int getRoll() {
        return Roll;
    }
}
