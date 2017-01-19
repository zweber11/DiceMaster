package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 12/28/2016.
 */
public class Skill {

    public int SkillID;
    public String SkillName;
    public String LastUsed;
    public int Favorite;

    //Empty constructor
    public Skill() {

    }

    //Loaded constructor
    public Skill(int skillID, String skillName, String lastUsed, int favorite) {
        SkillID = skillID;
        SkillName = skillName;
        LastUsed = lastUsed;
        Favorite = favorite;
    }

    //Getter-setters
    public int getSkillID() {
        return SkillID;
    }

    public void setSkillID(int skillID) {
        SkillID = skillID;
    }

    public String getSkillName() {
        return SkillName;
    }

    public void setSkillName(String skillName) {
        SkillName = skillName;
    }

    public String getLastUsed() {
        return LastUsed;
    }

    public void setLastUsed(String lastUsed) {
        LastUsed = lastUsed;
    }

    public int getFavorite() {
        return Favorite;
    }

    public void setFavorite(int favorite) {
        Favorite = favorite;
    }
}
