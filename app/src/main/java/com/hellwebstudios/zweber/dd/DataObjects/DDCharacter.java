package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 12/28/2016.
 */
public class DDCharacter {

    public int CharacterID;
    public String CharacterName;
    public int CharClassID;
    public int CharRaceID;

    //Empty Constructor
    public DDCharacter()
    {

    }

    //Loaded Constructor
    public DDCharacter(int characterID, String characterName, int charClassID, int charRaceID) {
        CharacterID = characterID;
        CharacterName = characterName;
        CharClassID = charClassID;
        CharRaceID = charRaceID;
    }

    //Getter-setters
    public int getCharacterID() {
        return CharacterID;
    }

    public void setCharacterID(int characterID) {
        CharacterID = characterID;
    }

    public String getCharacterName() {
        return CharacterName;
    }

    public void setCharacterName(String characterName) {
        CharacterName = characterName;
    }

    public int getCharClassID() {
        return CharClassID;
    }

    public void setCharClassID(int charClassID) {
        CharClassID = charClassID;
    }

    public int getCharRaceID() {
        return CharRaceID;
    }

    public void setCharRaceID(int charRaceID) {
        CharRaceID = charRaceID;
    }
}
