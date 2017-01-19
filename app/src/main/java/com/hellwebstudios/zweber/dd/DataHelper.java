package com.hellwebstudios.zweber.dd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hellwebstudios.zweber.dd.DataObjects.Adventure;
import com.hellwebstudios.zweber.dd.DataObjects.Chapter;
import com.hellwebstudios.zweber.dd.DataObjects.CharClass;
import com.hellwebstudios.zweber.dd.DataObjects.CharRace;
import com.hellwebstudios.zweber.dd.DataObjects.DDCharacter;
import com.hellwebstudios.zweber.dd.DataObjects.DiceSet;
import com.hellwebstudios.zweber.dd.DataObjects.DiceSetDie;
import com.hellwebstudios.zweber.dd.DataObjects.RollAttack;
import com.hellwebstudios.zweber.dd.DataObjects.RollAttackSet;
import com.hellwebstudios.zweber.dd.DataObjects.RollSkill;
import com.hellwebstudios.zweber.dd.DataObjects.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zweber on 12/28/2016.
 */
public class DataHelper extends SQLiteOpenHelper {

    //DB info
    public static final String DATABASE_NAME = "DM.db";
    public static final String T_CHAR = "Characters"; //12/28/2016 v1
    public static final String T_SKI = "Skills"; //12/28/2016 v1
    public static final String T_CLASS = "CharacterClasses"; //12/31/2016 v1
    public static final String T_RACE = "CharacterRaces"; //12/31/2016 v1
    public static final String T_DICE = "Dice"; //01/03/2017 v1
    public static final String T_ADV = "Adventures"; //01/04/2017
    public static final String T_CHAP = "Chapters"; //01/04/2017

    public static final String T_DS = "DiceSets"; //01/06/2017 v1
    public static final String T_DSD = "DiceSetDie"; //01/06/2017 v1

    public static final String T_RS = "RollSkills"; //01/08/2017
    public static final String T_RA = "RollAttacks"; //01/08/2017
    public static final String T_RAS = "RollAttackSets"; //01/10/2017

    //DB Version. 1 = 1.0
    public static final int DB_VERSION = 1;

    //region table column variables.

    //Character table column values
    public static final String C_1 = "CharacterName";
    public static final String C_2 = "CharClassID";
    public static final String C_3 = "CharRaceID";

    //CharClass table
    public static final String CC_1 = "ClassName";

    //CharRace table
    public static final String CR_1 = "RaceName";

    //Skills table column values
    public static final String SKI_2 = "LastUsed";
//    public static final String SKI_3 = "Favorite";

    //DiceSets table.
    public static final String DS_1 = "Name";
    public static final String DS_2 = "CharID";

    //DiceSetDie table.
    public static final String DSD_1 = "DiceSetID";
    public static final String DSD_2 = "DiceID";

    //Adventures table.
    public static final String ADV_1 = "Name";
    public static final String ADV_2 = "Desc";
    public static final String ADV_3 = "CharID";
    public static final String ADV_4 = "NumChapters";

    //Chapters table.
    public static final String CH_1 = "AdvID";
    public static final String CH_2 = "Name";

    //RollAttack table.
    public static final String RA_1 = "RASID";
    public static final String RA_2 = "DID";
    public static final String RA_3 = "Roll";

    //RollAttackSet table.
    public static final String RAS_1 = "ChapID";
    public static final String RAS_2 = "DSID";

    //RollSkill table.
    public static final String RS_1 = "SkillID";
    public static final String RS_2 = "ChapID";
    public static final String RS_3 = "Roll";

    //endregion


    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        //region **Classes/Races/Characters**

        //Initialize the Class/Race tables.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_CLASS + " (ClassID INTEGER PRIMARY KEY AUTOINCREMENT, ClassName TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_RACE + " (RaceID INTEGER PRIMARY KEY AUTOINCREMENT, RaceName TEXT)");

        //Create String[]'s for Class/Races Names. Used to create Default Classes/Races.
        String[] classes = {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Sorcerer", "Rogue", "Warlock", "Wizard"};
        String[] races = {"Aasimar", "Dragonborn", "Drawf", "Elf", "Firbolg", "Gnome", "Goliath", "Halfling", "Human", "Half-Elf", "Half-Orc", "Kenku", "Lizardfolk", "Tabaxi", "Tiefling", "Triton"};

        //Classes Loop.
        int cID = 1;
        for (String c : classes) {
            db.execSQL("INSERT OR IGNORE INTO " + T_CLASS + "(ClassID, ClassName) VALUES (" + cID + ", '" + c + "')");
            cID++;
        }

        //Races Loop.
        int rID = 1;
        for (String r : races) {
            db.execSQL("INSERT OR IGNORE INTO " + T_RACE + " (RaceID, RaceName) VALUES (" + rID + ", '" + r + "')");
            rID++;
        }

        //Characters table w/ data.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_CHAR + " (CharacterID INTEGER PRIMARY KEY AUTOINCREMENT, CharacterName TEXT, " +
                "CharClassID INTEGER REFERENCES CharacterClasses (ClassID), " +
                "CharRaceID INTEGER REFERENCES CharacterRaces (RaceID))");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAR + "(CharacterID, CharacterName, CharClassID, CharRaceID) VALUES(1, 'Hans', 6, 3)"); //Hans, the Monk Dwarf...
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAR + "(CharacterID, CharacterName, CharClassID, CharRaceID) VALUES(2, 'Leoz', 5, 4)");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAR + "(CharacterID, CharacterName, CharClassID, CharRaceID) VALUES(3, 'Haas', 4, 5)");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAR + "(CharacterID, CharacterName, CharClassID, CharRaceID) VALUES(4, 'Ofah', 3, 6)");

        //endregion

        //region **Adventures/Chapters**


        //Adventures table w/ data.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_ADV + " (AdvID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT, " +
                "Desc TEXT, " +
                "CharID INTEGER REFERENCES Characters (CharacterID), " +
                "NumChapters INTEGER)");
        db.execSQL("INSERT OR IGNORE INTO " + T_ADV + " (AdvID, Name, Desc, CharID, NumChapters) VALUES (1, 'Elgost in Peril', 'Join Hans on his quest.', 1, 3)");
        db.execSQL("INSERT OR IGNORE INTO " + T_ADV + " (AdvID, Name, Desc, CharID, NumChapters) VALUES (2, 'The Chosen Ones', 'Four enter, but will any survive?', 2, 3)");

        //Chapters table w/ data.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_CHAP + " (ChapID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "AdvID INTEGER REFERENCES Adventures (AdvID), " +
                "Name TEXT)");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAP + "(ChapID, AdvID, Name) VALUES (1, 1, 'Chapter 1: Humble Beginnings')");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAP + "(ChapID, AdvID, Name) VALUES (2, 1, 'Chapter 2: The Great Bridge')");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAP + "(ChapID, AdvID, Name) VALUES (3, 1, 'Chapter 3: Team Cohesion')");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAP + "(ChapID, AdvID, Name) VALUES (4, 2, 'Chapter 1: Formation')");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAP + "(ChapID, AdvID, Name) VALUES (5, 2, 'Chapter 2: Training')");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAP + "(ChapID, AdvID, Name) VALUES (6, 2, 'Chapter 3: Night Raid')");


        //endregion

        //region **RollSkills/RollAttackSets/RollAttacks**


        //RollSkills table w/ data.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_RS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SkillID INTEGER REFERENCES Skills (SkillID), " +
                "ChapID INTEGER REFERENCES Chapters (ChapID)," +
                "Roll INTEGER)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RS + " (ID, SkillID, ChapID, Roll) VALUES (1, 3, 1, 14)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RS + " (ID, SkillID, ChapID, Roll) VALUES (2, 15, 1, 12)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RS + " (ID, SkillID, ChapID, Roll) VALUES (3, 8, 1, 7)");

        //RollAttackSets table w/ data.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_RAS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ChapID INTEGER REFERENCES Chapters (ChapID), " +
                "DSID INTEGER REFERENCES DiceSets (ID))");
        db.execSQL("INSERT OR IGNORE INTO " + T_RAS + " (ID, ChapID, DSID) VALUES (1, 1, 1)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RAS + " (ID, ChapID, DSID) VALUES (2, 1, 1)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RAS + " (ID, ChapID, DSID) VALUES (3, 1, 1)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RAS + " (ID, ChapID, DSID) VALUES (4, 1, 1)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RAS + " (ID, ChapID, DSID) VALUES (5, 1, 1)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RAS + " (ID, ChapID, DSID) VALUES (6, 1, 1)");

        //RollAttacks table w/ data.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_RA + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "RASID INTEGER REFERENCES RollAttackSets (ID), " +
                "DID INTEGER REFERENCES Dice (DiceID), " +
                "Roll INTEGER)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RA + " (ID, RASID, DID, Roll) VALUES (1, 1, 1, 2)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RA + " (ID, RASID, DID, Roll) VALUES (2, 1, 3, 7)");


        //endregion

        //region **Skills**


        //Skills table.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_SKI + " (SkillID INTEGER PRIMARY KEY AUTOINCREMENT, SkillName TEXT, LastUsed TEXT, Favorite INTEGER)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (1, 'Acrobatics', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (2, 'Arcana', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (3, 'Athletics', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        //Charisma
        //Constitution
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (4, 'Deception', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        //Dexterity
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (5, 'History', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (6, 'Insight', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        //Intelligence
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (7, 'Intimidation', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (8, 'Investigation', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (9, 'Medicine', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (10, 'Nature', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (11, 'Perception', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (12, 'Performance', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (13, 'Religion', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (14, 'Sleight Of Hand', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (15, 'Stealth', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        //Strength
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (16, 'Survival', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        //Wisdom

        ///Save rolls
        //Charisma
        //Constitution
        //Dexterity
        //Intelligence
        //Strength
        //Wisdom


        //endregion

        //region **Dice/DiceSets**

        //Dice table.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_DICE + "(DiceID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Description TEXT)");
        db.execSQL("INSERT OR IGNORE INTO  " + T_DICE + "(DiceID, Name, Description) VALUES (1, 'D4', '1-4')"); //D4
        db.execSQL("INSERT OR IGNORE INTO  " + T_DICE + "(DiceID, Name, Description) VALUES (2, 'D6', '1-6')"); //D6
        db.execSQL("INSERT OR IGNORE INTO  " + T_DICE + "(DiceID, Name, Description) VALUES (3, 'D8', '1-8')"); //D8
        db.execSQL("INSERT OR IGNORE INTO  " + T_DICE + "(DiceID, Name, Description) VALUES (4, 'D10', '1-10')"); //D10
        db.execSQL("INSERT OR IGNORE INTO  " + T_DICE + "(DiceID, Name, Description) VALUES (5, 'D12', '1-12')"); //D12
//        db.execSQL("INSERT OR IGNORE INTO  " + T_DICE + "(DiceID, Name, Description) VALUES (6, 'D20', '1-20')"); //D20
//        db.execSQL("INSERT OR IGNORE INTO  " + T_DICE + "(DiceID, Name, Description) VALUES (7, '%', '1-100')"); //Percentile Dice

        //DiceSets table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_DS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, CharID INTEGER REFERENCES Characters (CharID))");
        db.execSQL("INSERT OR IGNORE INTO " + T_DS + " (ID, Name, CharID) VALUES (1, 'Jolt', 1)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DS + " (ID, Name, CharID) VALUES (2, 'Thunder', 2)");

        //DiceSetDie
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_DSD + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DiceSetID INTEGER REFERENCES DiceSets (ID), " +
                "DiceID INTEGER REFERENCES Dice (DiceID))");
        db.execSQL("INSERT OR IGNORE INTO " + T_DSD + " (ID, DiceSetID, DiceID) VALUES (1, 1, 1)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DSD + " (ID, DiceSetID, DiceID) VALUES (2, 1, 3)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DSD + " (ID, DiceSetID, DiceID) VALUES (3, 2, 2)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DSD + " (ID, DiceSetID, DiceID) VALUES (4, 2, 2)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DSD + " (ID, DiceSetID, DiceID) VALUES (5, 2, 5)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DSD + " (ID, DiceSetID, DiceID) VALUES (6, 2, 5)");

        //endregion

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }

    //region **DDCharacter table calls.

    //getAllCharacters
    public Cursor getAllCharacters() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_CHAR, null);
        return res;
    }

    //addCharacter
    public void addCharacter(DDCharacter newChar)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(C_1, newChar.CharacterName);
        contentValues.put(C_2, newChar.CharClassID);
        contentValues.put(C_3, newChar.CharRaceID);

        db.insert(T_CHAR, null, contentValues);
    }

    //updateCharacter(CHAR)
    public void updateCharacter(DDCharacter charToUpdate)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(C_1, charToUpdate.CharacterName);
        cv.put(C_2, charToUpdate.CharClassID);
        cv.put(C_3, charToUpdate.CharRaceID);

        String strFilter = "CharacterID=" + charToUpdate.CharacterID;

        db.update(T_CHAR, cv, strFilter, null);
    }

    //getChar(charID)
    public DDCharacter getChar(int charID)
    {
        DDCharacter charFromDB = new DDCharacter();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_CHAR + " WHERE CharacterID = " + charID, null);

        while (res.moveToNext()) {
            charFromDB.CharacterID = res.getInt(0);
            charFromDB.CharacterName = res.getString(1);
            charFromDB.CharClassID = res.getInt(2);
            charFromDB.CharRaceID = res.getInt(3);
        }

        return charFromDB;
    }

    //getCharName(charID)
    public String getCharName(int charID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT CharacterName FROM " + T_CHAR + " WHERE CharacterID = " + charID, null);

        String cn = "";
        while (res.moveToNext())
            cn = res.getString(0);

        return cn;
    }


    //endregion

    //region **CharClass table calls.

    //getAllClasses
    public Cursor getAllClasses() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_CLASS, null);
        return res;
    }

    //addClass
    public boolean addClass(CharClass newClass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CC_1, newClass.ClassName);

        long result = db.insert(T_CLASS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //updateClass
    public boolean updateClass(CharClass cToUpdate)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CC_1, cToUpdate.ClassName);

        String strFilter = "ClassID=" + cToUpdate.ClassID;

        long result = db.update(T_CLASS, cv, strFilter, null);
        if (result == -1)
            return false;
        else
            return true;
    }

    //getClass
    public CharClass getClass(int classID)
    {
        CharClass cFromDB = new CharClass();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_CLASS + " WHERE ClassID = " + classID, null);

        while (res.moveToNext()) {
            cFromDB.ClassID = res.getInt(0);
            cFromDB.ClassName = res.getString(1);
        }

        return cFromDB;
    }

    //getCN
    public String getCN(int classID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT ClassName FROM " + T_CLASS + " WHERE ClassID = " + classID, null);

        String cn = "";
        while (res.moveToNext())
            cn = res.getString(0);

        return cn;
    }

    //endregion

    //region **CharRace table calls.

    //getAllRaces
    public Cursor getAllRaces() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_RACE, null);
        return res;
    }

    //addRace
    public boolean addRace(CharRace newRace)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CR_1, newRace.RaceName);

        long result = db.insert(T_RACE, null, contentValues);
        if (result == -1)
        {
            return false;
        }
        else
            return true;
    }

    //updateRace
    public boolean updateRace(CharRace crToUpdate)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CR_1, crToUpdate.RaceName);

        String strFilter = "RaceID=" + crToUpdate.RaceID;

        long result = db.update(T_RACE, cv, strFilter, null);
        if (result == -1)
            return false;
        else
            return true;
    }

    //getClass
    public CharRace getRace(int raceID)
    {
        CharRace crFromDB = new CharRace();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_RACE + " WHERE RaceID = " + raceID, null);

        while (res.moveToNext())
        {
            crFromDB.RaceID = res.getInt(0);
            crFromDB.RaceName = res.getString(1);
        }

        return crFromDB;
    }

    //getRN
    public String getRN(int raceID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT RaceName FROM " + T_RACE + " WHERE RaceID = " + raceID, null);

        String rn = "";
        while (res.moveToNext())
            rn = res.getString(0);

        return rn;
    }

    //endregion

    //region **Skills table calls.

    //getAllSkills
    public Cursor getAllSkills() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_SKI, null);
        return res;
    }

    //updateSkill
//    public boolean updateSkillFavFlag(int skillID, int value)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put("SkillID", skillID);
//        cv.put(SKI_3, value);
//
//        String strFilter = "SkillID=" + skillID;
//
//        long result = db.update(T_SKI, cv, strFilter, null);
//        if (result == -1)
//            return false;
//        else
//            return true;
//    }

    //getSkill(int skillID)
    public Skill getSkill(int skillID)
    {
        Skill sFromDB = new Skill();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_SKI + " WHERE SkillID = " + skillID, null);

        while (res.moveToNext())
        {
            sFromDB.SkillID = res.getInt(0);
            sFromDB.SkillName = res.getString(1);
            sFromDB.LastUsed = res.getString(2);
            sFromDB.Favorite = res.getInt(3);
        }

        return sFromDB;
    }

    //getSkillName(id)
    public String getSkillName(int ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SkillName FROM " + T_SKI + " WHERE SkillID = " + ID, null);

        String sn = "";
        while (res.moveToNext())
            sn = res.getString(0);

        return sn;
    }

    //getSkillsByFav --v2 call...
//    public Cursor getSkillsByFav() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + T_SKI + " ORDER BY Favorite DESC", null);
//        return res;
//    }

    //getRecentSkills()
    public Cursor getRecentSkills() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_SKI + " ORDER BY LastUsed DESC", null);
        return res;
    }

    //updateSkillDT(skillID)
    public void updateSkillDT(int skillID, String newDT)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("SkillID", skillID);
        cv.put(SKI_2, newDT);

        String strFilter = "SkillID=" + skillID;
        db.update(T_SKI, cv, strFilter, null);
    }

    //getSIDBySN(String SN)
    public int getSIDBySN(String name)
    {
        int sID = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SkillID FROM " + T_SKI + " WHERE SkillName = '" + name + "'", null);

        while (res.moveToNext())
            sID = res.getInt(0);

        return sID;
    }


    //endregion

    //region **Adventure calls.**

    //Adventure calls.
    //getAllAdv
    public Cursor getAllAdv() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_ADV, null);
        return res;
    }

    //addAdv
    public boolean addAdv(Adventure adv)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ADV_1, adv.Name);
        cv.put(ADV_2, adv.Desc);
        cv.put(ADV_3, adv.CharID);
        cv.put(ADV_4, adv.NumChapters);

        long result = db.insert(T_ADV, null, cv);
        if (result == -1)
        {
            return false;
        }
        else
            return true;
    }

    //updateAdv(Adv)
    public boolean updateAdv(Adventure adv)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ADV_1, adv.Name);
        cv.put(ADV_2, adv.Desc);
        cv.put(ADV_3, adv.CharID);
        cv.put(ADV_4, adv.NumChapters);

        String strFilter = "AdvID=" + adv.AdvID;

        long result = db.update(T_ADV, cv, strFilter, null);
        if (result == -1)
            return false;
        else
            return true;
    }

    //getAdv(advID)
    public Adventure getAdv(int advID)
    {
        Adventure adv = new Adventure();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_ADV + " WHERE AdvID = " + advID, null);

        while (res.moveToNext())
        {
            adv.AdvID = res.getInt(0);
            adv.Name = res.getString(1);
            adv.Desc = res.getString(2);
            adv.CharID = res.getInt(3);
            adv.NumChapters = res.getInt(4);
        }

        return adv;
    }

    //getAdvTitle(int advID)
    public String getAdvTitle(int advID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Name FROM " + T_ADV + " WHERE AdvID = " + advID, null);

        String at = "";
        while (res.moveToNext())
            at = res.getString(0);

        return at;
    }

    //endregion

    //region **Chapter calls.**


    //addChap
    public boolean addChap(Chapter c)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CH_1, c.AdvID);
        cv.put(CH_2, c.Name);

        long result = db.insert(T_CHAP, null, cv);
        if (result == -1)
            return false;
        else
            return true;
    }

    //getChapTitle(int chapID)
    public String getChapTitle(int chapID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Name FROM " + T_CHAP + " WHERE ChapID = " + chapID, null);

        String ct = "";
        while (res.moveToNext())
            ct = res.getString(0);

        return ct;
    }

    //getChapsByAdv(int advID)
    public Cursor getChapsByAdv(int a)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_CHAP + " WHERE AdvID = " + a, null);
        return res;
    }


    //endregion

    //region **Dice table calls.


    //getAllDie
    public Cursor getAllDie() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_DICE, null);
        return res;
    }

    //getUpDie(DiceID)
    public Cursor getUpDie(int DiceID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_DICE + " WHERE DiceID > " + DiceID, null);
        return res;
    }


    //getDiceName(ID)
    public String getDiceName(int ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Name FROM " + T_DICE + " WHERE DiceID = " + ID, null);

        String dn = "";
        while (res.moveToNext())
            dn = res.getString(0);

        return dn;
    }

    //getDiceID(int DSDID)iD
    public int getDiceID(int DSDID)
    {
        int diceID = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT DiceID FROM " + T_DSD + " WHERE ID = " + DSDID, null);

        while (res.moveToNext())
            diceID = res.getInt(0);

        return diceID;
    }

    //getDSD(int DSDID)
    public DiceSetDie getDSD(int DSDID)
    {
        DiceSetDie dsd = new DiceSetDie();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_DSD + " WHERE ID = " + DSDID, null);

        while (res.moveToNext())
        {
            dsd.ID = res.getInt(0);
            dsd.DiceSetID = res.getInt(1);
            dsd.DiceID = res.getInt(2);
        }

        return dsd;
    }


    //endregion

    //region **DiceSet table calls.


    //getAllDS
    public Cursor getAllDS() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_DS, null);
        return res;
    }

    //addDS
    public boolean addDS(DiceSet newDS)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DS_1, newDS.Name);
        cv.put(DS_2, newDS.CharID);

        long result = db.insert(T_DS, null, cv);
        if (result == -1)
            return false;
        else
            return true;
    }

    //getDSNameByID
    public String getDSNameByID(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Name FROM " + T_DS + " WHERE ID = " + id, null);

        String dsn = "";
        while (res.moveToNext())
            dsn = res.getString(0);

        return dsn;
    }


    //endregion

    //region ***DiceSetDie table calls.


    //addDSD
    public boolean addDSD(DiceSetDie dsd)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DSD_1, dsd.DiceSetID);
        cv.put(DSD_2, dsd.DiceID);

        long result = db.insert(T_DSD, null, cv);
        if (result == -1)
            return false;
        else
            return true;
    }

    //getDSDByDSID(int dsID)
    public Cursor getDSDByDSID(int dsID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_DSD + " WHERE DiceSetID = " + dsID + " ORDER BY DiceID", null);
        return res;
    }

    //updateDSD(DSD dsdUp)
    public void updateDSD(DiceSetDie dsdUp)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DSD_1, dsdUp.DiceSetID);
        cv.put(DSD_2, dsdUp.DiceID);

        String f = "ID=" + dsdUp.ID;

        db.update(T_DSD, cv, f, null);

    }

//    public void updateRS(RollSkill rs)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(RS_1, rs.SkillID);
//        cv.put(RS_2, rs.ChapID);
//        cv.put(RS_3, rs.Roll);
//
//        String f = "ID=" + rs.ID;
//
//        db.update(T_RS, cv, f, null);
//    }

    //endregion

    //region **RollLog table calls.

    //addRollLog
//    public boolean addRollLog(RollLog rl)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(RL_1, rl.ChapID);
//        cv.put(RL_2, rl.AttackSkill);
//
//        long result = db.insert(T_RL, null, cv);
//        if (result == -1)
//            return false;
//        else
//            return true;
//    }
//
//    //getRLByType(int typeID)
//    public Cursor getRLByType(int typeID)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + T_RL + " WHERE AttackSkill = " + typeID, null);
//        return res;
//    }

    //endregion

    //region **RollSkill table calls.

    //addRS(rs)
    public boolean addRS(RollSkill rs)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(RS_1, rs.SkillID);
        cv.put(RS_2, rs.ChapID);
        cv.put(RS_3, rs.Roll);

        long result = db.insert(T_RS, null, cv);
        if (result == -1)
            return false;
        else
            return true;
    }

    //updateRS(RollSkill rs)
    public void updateRS(RollSkill rs)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(RS_1, rs.SkillID);
        cv.put(RS_2, rs.ChapID);
        cv.put(RS_3, rs.Roll);

        String f = "ID=" + rs.ID;

        db.update(T_RS, cv, f, null);
    }

    //getRSByChapID(int chapID)
    public Cursor getRSByChapID(int chapID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_RS + " WHERE ChapID = " + chapID, null);
        return res;
    }

    //getRollByRSID(int RSID)
    public int getRollByRSID(int RSID)
    {
        int r = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Roll FROM " + T_RS + " WHERE ID = " + RSID, null);

        while (res.moveToNext())
            r = res.getInt(0);

        return r;
    }

    //getSkillByRSID(int RSID)
    public int getSkillByRSID(int RSID)
    {
        int r = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SkillID FROM " + T_RS + " WHERE ID = " + RSID, null);

        while (res.moveToNext())
            r = res.getInt(0);

        return r;
    }

    //endregion

    //region **RollAttack table calls.

    //GetDieByDSID
    public Cursor getDieByDSID(int dsID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_DSD +  " WHERE DiceSetID = " + dsID, null);

        return res;
    }

    //addRA(RollAttack ra)
    public void addRA(RollAttack ra)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(RA_1, ra.RASID);
        cv.put(RA_2, ra.DID);
        cv.put(RA_3, ra.Roll);

        db.insert(T_RA, null, cv);
    }

    //getRAByRASID(int RASID)
    public Cursor getRAByRASID(int RASID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_RA + " WHERE RASID = " + RASID + " ORDER BY DID", null);

        return res;
    }

    //updateRA(RollAttack ra)
    public void updateRA(RollAttack ra)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(RA_1, ra.RASID);
        cv.put(RA_2, ra.DID);
        cv.put(RA_3, ra.Roll);

        String f = "ID=" + ra.ID;

        db.update(T_RA, cv, f, null);
    }

    //getRA(int RAID)
    public RollAttack getRA(int RAID)
    {
        RollAttack ra = new RollAttack();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_RA + " WHERE ID = " + RAID, null);

        while (res.moveToNext())
        {
            ra.ID = res.getInt(0);
            ra.RASID = res.getInt(1);
            ra.DID = res.getInt(2);
            ra.Roll = res.getInt(3);
        }

        return ra;
    }

    //endregion

    //region **RollAttackSet table calls.**

    //addRAS(ras)
    public void addRAS(RollAttackSet ras)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(RAS_1, ras.ChapID);
        cv.put(RAS_2, ras.DSID);

        db.insert(T_RAS, null, cv);
    }

    //getRASetsByChapID
    public Cursor getRASetsByChapID(int chapID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + T_RAS + " WHERE ChapID = " + chapID, null);
        return res;
    }

    //getDSIDByRASID(int RASID)
    public int getDSIDByRASID(int RASID)
    {
        int dsid = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT DSID FROM " + T_RAS + " WHERE ID = " + RASID, null);

        while (res.moveToNext())
            dsid = res.getInt(0);

        return dsid;
    }

    //getLatestRASID()
    public int getLatestRASID()
    {
        int r = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT ID FROM " + T_RAS + " ORDER BY ID DESC LIMIT 1", null);

        while (res.moveToNext())
            r = res.getInt(0);

        return r;
    }

    //endregion

}