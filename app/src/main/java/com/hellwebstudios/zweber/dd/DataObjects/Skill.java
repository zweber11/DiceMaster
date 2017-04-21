package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 12/28/2016.
 */
public class Skill {

    public int SkillID;
    public String SkillName;
    public String LastUsed;
    public int Favorite;

    public Skill() { }
    public Skill(int skillID, String skillName, String lastUsed, int favorite) {
        SkillID = skillID;
        SkillName = skillName;
        LastUsed = lastUsed;
        Favorite = favorite;
    }

    //Getter-setters
    public int getSkillID() { return SkillID; }
    public String getSkillName() { return SkillName; }
    public String getLastUsed() { return LastUsed; }
}
