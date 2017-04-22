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
import com.hellwebstudios.zweber.dd.DataObjects.DashboardGrid;
import com.hellwebstudios.zweber.dd.DataObjects.DashboardSetting;
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

    //DB table names.
    private static final String DATABASE_NAME = "DM.db";
    private static final String T_CHAR = "Characters"; //12/28/2016 v1
    private static final String T_SKI = "Skills"; //12/28/2016 v1
    private static final String T_CLASS = "CharacterClasses"; //12/31/2016 v1
    private static final String T_RACE = "CharacterRaces"; //12/31/2016 v1
    private static final String T_DICE = "Dice"; //01/03/2017 v1
    private static final String T_ADV = "Adventures"; //01/04/2017 v1
    private static final String T_CHAP = "Chapters"; //01/04/2017 v1
    private static final String T_DS = "DiceSets"; //01/06/2017 v1
    private static final String T_DSD = "DiceSetDie"; //01/06/2017 v1
    private static final String T_RS = "RollSkills"; //01/08/2017 v1
    private static final String T_RA = "RollAttacks"; //01/08/2017 v1
    private static final String T_RAS = "RollAttackSets"; //01/10/2017 v1

    private static final String T_DASH = "DashboardSettings"; //4/19/2017 v2
    private static final String T_DASH_GRID = "DashboardGrid"; //4/19/2017 v2

    //DB Version. 1 = 1.0, 2 = 1.5
    private static final int DB_VERSION = 2;

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
//    public static final String CH_3 = "RollToHit";

    //RollAttack table.
    public static final String RA_1 = "RASID";
    public static final String RA_2 = "DID";
    public static final String RA_3 = "Roll";

    //RollAttackSet table.
    public static final String RAS_1 = "ChapID";
    public static final String RAS_2 = "DSID";
    public static final String RAS_3 = "Initiative";
    public static final String RAS_4 = "RollToHit";

    //RollSkill table.
    public static final String RS_1 = "SkillID";
    public static final String RS_2 = "ChapID";
    public static final String RS_3 = "Roll";

    //endregion

    //Testing the global db call to reduce calls.
    private SQLiteDatabase db = this.getWritableDatabase();

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    public void genVerTwoData() {

        //region **DashboardSettings table.**

        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_DASH + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Description TEXT)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(1, 'Adventures', 'Allows you to add and view Adventures.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(2, 'Characters', 'Allows you to add and view Characters.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(3, 'Skills', 'Allows you to view Skills.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(4, 'Dice Sets', 'Allows you to add, edit, and view Dice Sets.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(5, 'Settings', 'Allows you to view and modify Settings.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(6, 'About', 'About page.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(7, 'Help', 'Link to a Google Doc containing Dice Master help information.')");

        //endregion

        //region **DashboardGrid table w/ initial data.**

        //Adv/Char flipped for testing purposes.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_DASH_GRID + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Position TEXT, DSID INTEGER REFERENCES DashboardSettings(ID), TileColor TEXT, TileTextColor TEXT)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (1, 'Upper Left Tile', 2, 3556946, 5685952)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (2, 'Upper Right Tile', 1, 3556946, 5685952)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (3, 'Middle Left Tile', 3, 3556946, 5685952)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (4, 'Middle Right Tile', 4, 3556946, 5685952)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (5, 'Lower Left Tile', 5, 3556946, 5685952)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (6, 'Lower Right Tile', 6, 3556946, 5685952)");

        //endregion

        //        3556946 (Primary Color grey)
        //        5685952 (secondary color, light blue)
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

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
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAR + "(CharacterID, CharacterName, CharClassID, CharRaceID) VALUES(1, 'Hans', 6, 3)"); //Hans, the Monk Dwarf.

        //endregion

        //region **Adventures/Chapters**

        //Adventures table w/ data.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_ADV + " (AdvID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT, " +
                "Desc TEXT, " +
                "CharID INTEGER REFERENCES Characters (CharacterID), " +
                "NumChapters INTEGER)");
        db.execSQL("INSERT OR IGNORE INTO " + T_ADV + " (AdvID, Name, Desc, CharID, NumChapters) VALUES (1, 'Paradise in Peril', 'Join Hans on his quest.', 1, 3)");

        //Chapters table w/ data.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_CHAP + " (ChapID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "AdvID INTEGER REFERENCES Adventures (AdvID), " +
                "Name TEXT)");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAP + "(ChapID, AdvID, Name) VALUES (1, 1, 'Chapter 1: Humble Beginnings')");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAP + "(ChapID, AdvID, Name) VALUES (2, 1, 'Chapter 2: The Great Bridge')");
        db.execSQL("INSERT OR IGNORE INTO " + T_CHAP + "(ChapID, AdvID, Name) VALUES (3, 1, 'Chapter 3: Team Cohesion')");

        //endregion

        //region **RollSkills/RollAttackSets/RollAttacks**

        //RollSkills table w/ data.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_RS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SkillID INTEGER REFERENCES Skills (SkillID), " +
                "ChapID INTEGER REFERENCES Chapters (ChapID)," +
                "Roll INTEGER)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RS + " (ID, SkillID, ChapID, Roll) VALUES (1, 3, 1, 14)");

        //RollAttackSets table w/ data.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_RAS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ChapID INTEGER REFERENCES Chapters (ChapID), " +
                "DSID INTEGER REFERENCES DiceSets (ID)," +
                "Initiative INTEGER," +
                "RollToHit INTEGER)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RAS + " (ID, ChapID, DSID, Initiative, RollToHit) VALUES (1, 1, 1, 1, 1)");

        //RollAttacks table w/ data.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_RA + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "RASID INTEGER REFERENCES RollAttackSets (ID), " +
                "DID INTEGER REFERENCES Dice (DiceID), " +
                "Roll INTEGER)");
        db.execSQL("INSERT OR IGNORE INTO " + T_RA + " (ID, RASID, DID, Roll) VALUES (1, 1, 4, 1)");

        //endregion

        //region **Skills**

        //Skills table.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_SKI + " (SkillID INTEGER PRIMARY KEY AUTOINCREMENT, SkillName TEXT, LastUsed TEXT, Favorite INTEGER)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (1, 'Acrobatics', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (2, 'Arcana', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (3, 'Athletics', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (4, 'Charisma (SR)', 'Sat Dec 31, 2016. 01:00 PM', 0)"); //Charisma
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (5, 'Construction (SR)', 'Sat Dec 31, 2016. 01:00 PM', 0)"); //Constitution
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (6, 'Deception', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (7, 'Dexterity (SR)', 'Sat Dec 31, 2016. 01:00 PM', 0)"); //Dexterity
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (8, 'History', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (9, 'Insight', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (10, 'Intelligence (SR)', 'Sat Dec 31, 2016. 01:00 PM', 0)"); //Intelligence
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (11, 'Intimidation', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (12, 'Investigation', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (13, 'Medicine', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (14, 'Nature', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (15, 'Perception', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (16, 'Performance', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (17, 'Religion', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (18, 'Sleight Of Hand', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (19, 'Stealth', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (20, 'Strength (SR)', 'Sat Dec 31, 2016. 01:00 PM', 0)"); //Strength
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (21, 'Survival', 'Sat Dec 31, 2016. 01:00 PM', 0)");
        db.execSQL("INSERT OR IGNORE INTO " + T_SKI + "(SkillID, SkillName, LastUsed, Favorite) VALUES (22, 'Wisdom (SR)', 'Sat Dec 31, 2016. 01:00 PM', 0)"); //Wisdom

        ///Save rolls: Charisma, Constitution, Dexterity, Intelligence, Strength, Wisdom

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

        //DiceSetDie
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_DSD + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DiceSetID INTEGER REFERENCES DiceSets (ID), " +
                "DiceID INTEGER REFERENCES Dice (DiceID))");
        db.execSQL("INSERT OR IGNORE INTO " + T_DSD + " (ID, DiceSetID, DiceID) VALUES (1, 1, 1)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DSD + " (ID, DiceSetID, DiceID) VALUES (2, 1, 3)");

        //endregion

//        genVerTwoData();

        //region **DashboardSettings table.**

        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_DASH + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Description TEXT)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(1, 'Adventures', 'Allows you to add and view Adventures.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(2, 'Characters', 'Allows you to add and view Characters.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(3, 'Skills', 'Allows you to view Skills.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(4, 'Dice Sets', 'Allows you to add, edit, and view Dice Sets.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(5, 'Settings', 'Allows you to view and modify Settings.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(6, 'About', 'About page.')");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(7, 'Help', 'Link to a Google Doc containing Dice Master help information.')");

        //endregion

        //region **DashboardGrid table w/ initial data.**

        //Adv/Char flipped for testing purposes.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + T_DASH_GRID + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Position TEXT, DSID INTEGER REFERENCES DashboardSettings(ID), TileColor TEXT, TileTextColor TEXT)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (1, 'Upper Left Tile', 1, 3556946, 5685952)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (2, 'Upper Right Tile', 2, 3556946, 5685952)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (3, 'Middle Left Tile', 3, 3556946, 5685952)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (4, 'Middle Right Tile', 4, 3556946, 5685952)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (5, 'Lower Left Tile', 5, 3556946, 5685952)");
        db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (6, 'Lower Right Tile', 6, 3556946, 5685952)");

        //endregion
//
//        //        3556946 (Primary Color grey)
//        //        5685952 (secondary color, light blue)
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DB Version Check.
        //Version 1 --> v2.
        if (oldVersion < 2) {

            //Call genVerTwoData, creating the two new tables.
//            genVerTwoData();

            //region **DashboardSettings table.**

            db.execSQL("CREATE TABLE IF NOT EXISTS " + T_DASH + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Description TEXT)");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(1, 'Adventures', 'Allows you to add and view Adventures.')");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(2, 'Characters', 'Allows you to add and view Characters.')");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(3, 'Skills', 'Allows you to view Skills.')");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(4, 'Dice Sets', 'Allows you to add, edit, and view Dice Sets.')");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(5, 'Settings', 'Allows you to view and modify Settings.')");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(6, 'About', 'About page.')");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH + "(ID, Name, Description) VALUES(7, 'Help', 'Link to a Google Doc containing Dice Master help information.')");

            //endregion

            //region **DashboardGrid table w/ initial data.**

            //Adv/Char flipped for testing purposes.
            db.execSQL("CREATE TABLE IF NOT EXISTS " + T_DASH_GRID + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Position TEXT, DSID INTEGER REFERENCES DashboardSettings(ID), TileColor TEXT, TileTextColor TEXT)");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (1, 'Upper Left Tile', 1, 3556946, 5685952)");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (2, 'Upper Right Tile', 2, 3556946, 5685952)");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (3, 'Middle Left Tile', 3, 3556946, 5685952)");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (4, 'Middle Right Tile', 4, 3556946, 5685952)");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (5, 'Lower Left Tile', 5, 3556946, 5685952)");
            db.execSQL("INSERT OR IGNORE INTO " + T_DASH_GRID + "(ID, Position, DSID, TileColor, TileTextColor) VALUES (6, 'Lower Right Tile', 6, 3556946, 5685952)");

            //endregion

            //Update skills table Save Roll entries to shorten them for better data display.
            db.execSQL("UPDATE " + T_SKI + " SET SkillName = 'Charisma (SR)' WHERE SkillID = 4");
            db.execSQL("UPDATE " + T_SKI + " SET SkillName = 'Construction (SR)' WHERE SkillID = 5");
            db.execSQL("UPDATE " + T_SKI + " SET SkillName = 'Dexterity (SR)' WHERE SkillID = 7");
            db.execSQL("UPDATE " + T_SKI + " SET SkillName = 'Intelligence (SR)' WHERE SkillID = 10");
            db.execSQL("UPDATE " + T_SKI + " SET SkillName = 'Strength (SR)' WHERE SkillID = 20");
            db.execSQL("UPDATE " + T_SKI + " SET SkillName = 'Wisdom (SR)' WHERE SkillID = 22");
        }

        onCreate(db);
    }

    //region **DDCharacter table calls.

    //getAllCharacters
    public Cursor getAllCharacters() {
        Cursor res = db.rawQuery("SELECT * FROM " + T_CHAR, null);
        return res;
    }

    //addCharacter
    public void addCharacter(DDCharacter newChar)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(C_1, newChar.CharacterName);
        contentValues.put(C_2, newChar.CharClassID);
        contentValues.put(C_3, newChar.CharRaceID);

        db.insert(T_CHAR, null, contentValues);
    }

    //updateCharacter(CHAR)
    public void updateCharacter(DDCharacter charToUpdate)
    {
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
        Cursor res = db.rawQuery("SELECT * FROM " + T_CLASS, null);
        return res;
    }

    //addClass
    public boolean addClass(CharClass newClass)
    {
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
        Cursor res = db.rawQuery("SELECT * FROM " + T_RACE, null);
        return res;
    }

    //addRace
    public boolean addRace(CharRace newRace) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CR_1, newRace.RaceName);

        long result = db.insert(T_RACE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //updateRace
    public boolean updateRace(CharRace crToUpdate)
    {
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
        Cursor res = db.rawQuery("SELECT * FROM " + T_RACE + " WHERE RaceID = " + raceID, null);

        while (res.moveToNext()) {
            crFromDB.RaceID = res.getInt(0);
            crFromDB.RaceName = res.getString(1);
        }

        return crFromDB;
    }

    //getRN
    public String getRN(int raceID)
    {
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
        Cursor res = db.rawQuery("SELECT * FROM " + T_SKI, null);
        return res;
    }

    //updateSkill
//    public boolean updateSkillFavFlag(int skillID, int value)
//    {
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
        Cursor res = db.rawQuery("SELECT * FROM " + T_SKI + " WHERE SkillID = " + skillID, null);

        while (res.moveToNext()) {
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
        Cursor res = db.rawQuery("SELECT SkillName FROM " + T_SKI + " WHERE SkillID = " + ID, null);

        String sn = "";
        while (res.moveToNext())
            sn = res.getString(0);

        return sn;
    }

    //getSkillsByFav --v2 call...
//    public Cursor getSkillsByFav() {
//        Cursor res = db.rawQuery("SELECT * FROM " + T_SKI + " ORDER BY Favorite DESC", null);
//        return res;
//    }

    //getRecentSkills()
    public Cursor getRecentSkills() {
        Cursor res = db.rawQuery("SELECT * FROM " + T_SKI + " ORDER BY LastUsed DESC", null);
        return res;
    }

    //updateSkillDT(skillID)
    public void updateSkillDT(int skillID, String newDT)
    {
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
        Cursor res = db.rawQuery("SELECT SkillID FROM " + T_SKI + " WHERE SkillName = '" + name + "'", null);

        while (res.moveToNext())
            sID = res.getInt(0);

        return sID;
    }

    //getSNByID(int sID)
    public String getSNByID(int sID)
    {
        String n = "";
        Cursor res = db.rawQuery("SELECT SkillName FROM " + T_SKI + " WHERE SkillID = " + sID, null);

        while (res.moveToNext())
            n = res.getString(0);

        return n;
    }


    //endregion

    //region **Adventure calls.**

    //Adventure calls.
    //getAllAdv
    public Cursor getAllAdv() {
        Cursor res = db.rawQuery("SELECT * FROM " + T_ADV, null);
        return res;
    }

    //addAdv
    public void addAdv(Adventure adv) {
        ContentValues cv = new ContentValues();

        cv.put(ADV_1, adv.Name);
        cv.put(ADV_2, adv.Desc);
        cv.put(ADV_3, adv.CharID);
        cv.put(ADV_4, adv.NumChapters);

        db.insert(T_ADV, null, cv);
    }

    //updateAdv(Adv)
    public void updateAdv(Adventure adv) {
        ContentValues cv = new ContentValues();

        cv.put(ADV_1, adv.Name);
        cv.put(ADV_2, adv.Desc);
        cv.put(ADV_3, adv.CharID);
        cv.put(ADV_4, adv.NumChapters);

        String strFilter = "AdvID=" + adv.AdvID;

        db.update(T_ADV, cv, strFilter, null);
    }

    //getAdv(advID)
    public Adventure getAdv(int advID) {
        Adventure adv = new Adventure();
        Cursor res = db.rawQuery("SELECT * FROM " + T_ADV + " WHERE AdvID = " + advID, null);

        while (res.moveToNext()) {
            adv.AdvID = res.getInt(0);
            adv.Name = res.getString(1);
            adv.Desc = res.getString(2);
            adv.CharID = res.getInt(3);
            adv.NumChapters = res.getInt(4);
        }

        return adv;
    }

    //getAdvTitle(int advID)
    public String getAdvTitle(int advID) {
        Cursor res = db.rawQuery("SELECT Name FROM " + T_ADV + " WHERE AdvID = " + advID, null);

        String at = "";
        while (res.moveToNext())
            at = res.getString(0);

        return at;
    }

    //getCharIDByAdvID
    public int getCharIDByAdvID(int advID) {
        int charID = 0;
        Cursor res = db.rawQuery("SELECT CharID FROM " + T_ADV + " WHERE AdvID = " + advID, null);

        while (res.moveToNext())
            charID = res.getInt(0);

        return charID;
    }

    //endregion

    //region **Chapter calls.**


    //addChap
    public void addChap(Chapter c) {
        ContentValues cv = new ContentValues();

        cv.put(CH_1, c.AdvID);
        cv.put(CH_2, c.Name);

        db.insert(T_CHAP, null, cv);
    }

    //getChapTitle(int chapID)
    public String getChapTitle(int chapID) {
        Cursor res = db.rawQuery("SELECT Name FROM " + T_CHAP + " WHERE ChapID = " + chapID, null);

        String ct = "";
        while (res.moveToNext())
            ct = res.getString(0);

        return ct;
    }

    //getChapsByAdv(int advID)
    public Cursor getChapsByAdv(int a) {
        Cursor res = db.rawQuery("SELECT * FROM " + T_CHAP + " WHERE AdvID = " + a, null);
        return res;
    }

    //getChap(int chapID)
    public Chapter getChap(int chapID) {
        Chapter chap = new Chapter();
        Cursor res = db.rawQuery("SELECT * FROM " + T_CHAP + " WHERE ChapID = " + chapID, null);

        while (res.moveToNext()) {
            chap.ChapID = res.getInt(0);
            chap.AdvID = res.getInt(1);
            chap.Name = res.getString(2);
        }

        return chap;
    }

//    public Adventure getAdv(int advID) {
//        Adventure adv = new Adventure();
//        Cursor res = db.rawQuery("SELECT * FROM " + T_ADV + " WHERE AdvID = " + advID, null);
//
//        while (res.moveToNext()) {
//            adv.AdvID = res.getInt(0);
//            adv.Name = res.getString(1);
//            adv.Desc = res.getString(2);
//            adv.CharID = res.getInt(3);
//            adv.NumChapters = res.getInt(4);
//        }
//
//        return adv;
//    }


    //updateChap(chap)
    public void updateChap(Chapter c) {
        ContentValues cv = new ContentValues();
        cv.put(CH_1, c.AdvID);
        cv.put(CH_2, c.Name);

        String strFilter = "ChapID=" + c.ChapID;
        db.update(T_CHAP, cv, strFilter, null);
    }

    //endregion

    //region **Dice table calls.


    //getAllDie
    public Cursor getAllDie() {
        Cursor res = db.rawQuery("SELECT * FROM " + T_DICE, null);
        return res;
    }

    //getDiceName(ID)
    public String getDiceName(int ID) {
        Cursor res = db.rawQuery("SELECT Name FROM " + T_DICE + " WHERE DiceID = " + ID, null);

        String dn = "";
        while (res.moveToNext())
            dn = res.getString(0);

        return dn;
    }

    //getDSD(int DSDID)
    public DiceSetDie getDSD(int DSDID) {
        DiceSetDie dsd = new DiceSetDie();
        Cursor res = db.rawQuery("SELECT * FROM " + T_DSD + " WHERE ID = " + DSDID, null);

        while (res.moveToNext()) {
            dsd.ID = res.getInt(0);
            dsd.DiceSetID = res.getInt(1);
            dsd.DiceID = res.getInt(2);
        }

        return dsd;
    }


    //endregion

    //region **DiceSet table calls.

    //getDSByID(int DSID)
    public DiceSet getDSByID(int dsID) {
        DiceSet dsFromDB = new DiceSet();

        Cursor res = db.rawQuery("SELECT * FROM DiceSets WHERE ID = " + dsID, null);

        while (res.moveToNext()) {
            dsFromDB.ID = res.getInt(0);
            dsFromDB.Name = res.getString(1);
            dsFromDB.CharID = res.getInt(2);
        }

        return dsFromDB;
    }

    //getAllDS
    public Cursor getAllDS() {
        Cursor res = db.rawQuery("SELECT * FROM " + T_DS, null);
        return res;
    }

    //getCharDS(int charID)
    public Cursor getCharDS(int charID) {
        Cursor res = db.rawQuery("SELECT * FROM DiceSets WHERE CharID = " + charID, null);
        return res;
    }

    //addDS
    public boolean addDS(DiceSet newDS) {
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
    public String getDSNameByID(int id) {
        String dsn = "";
        Cursor res = db.rawQuery("SELECT Name FROM " + T_DS + " WHERE ID = " + id, null);

        while (res.moveToNext())
            dsn = res.getString(0);

        return dsn;
    }

    //getDSIDByName(string name)
    public int getDSIDByName(String name) {
        int id = 0;
        Cursor res = db.rawQuery("SELECT * FROM " + T_DS + " WHERE Name = '" + name + "'", null);

        while (res.moveToNext())
            id = res.getInt(0);

        return id;
    }

    //valDS(string DSN)
    public int valDS(String DSN) {
        int c = 0;
        Cursor res = db.rawQuery("SELECT Count(ID) FROM " + T_DS + " WHERE Name = '" + DSN + "'", null);

        while (res.moveToNext())
            c = res.getInt(0);

        return c;
    }

    //endregion

    //region ***DiceSetDie table calls.


    //addDSD
    public boolean addDSD(DiceSetDie dsd)
    {
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
    public Cursor getDSDByDSID(int dsID) {
        Cursor res = db.rawQuery("SELECT * FROM " + T_DSD + " WHERE DiceSetID = " + dsID + " ORDER BY DiceID", null);
        return res;
    }

    //updateDSD(DSD dsdUp)
    public void updateDSD(DiceSetDie dsdUp)
    {
        ContentValues cv = new ContentValues();

        cv.put(DSD_1, dsdUp.DiceSetID);
        cv.put(DSD_2, dsdUp.DiceID);

        String f = "ID=" + dsdUp.ID;

        db.update(T_DSD, cv, f, null);
    }


    //endregion

    //region **RollSkill table calls.

    //addRS(rs)
    public boolean addRS(RollSkill rs)
    {
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
        Cursor res = db.rawQuery("SELECT * FROM " + T_RS + " WHERE ChapID = " + chapID, null);
        return res;
    }

    //getRollByRSID(int RSID)
    public int getRollByRSID(int RSID)
    {
        int r = 0;
        Cursor res = db.rawQuery("SELECT Roll FROM " + T_RS + " WHERE ID = " + RSID, null);

        while (res.moveToNext())
            r = res.getInt(0);

        return r;
    }

    //getSkillByRSID(int RSID)
    public int getSkillByRSID(int RSID)
    {
        int r = 0;
        Cursor res = db.rawQuery("SELECT SkillID FROM " + T_RS + " WHERE ID = " + RSID, null);

        while (res.moveToNext())
            r = res.getInt(0);

        return r;
    }

    //endregion

    //region **RollAttack table calls.

    //GetDieByDSID
    public Cursor getDieByDSID(int dsID) {
        Cursor res = db.rawQuery("SELECT * FROM " + T_DSD +  " WHERE DiceSetID = " + dsID, null);
        return res;
    }

    //addRA(RollAttack ra)
    public void addRA(RollAttack ra)
    {
        ContentValues cv = new ContentValues();

        cv.put(RA_1, ra.RASID);
        cv.put(RA_2, ra.DID);
        cv.put(RA_3, ra.Roll);

        db.insert(T_RA, null, cv);
    }

    //getRAByRASID(int RASID)
    public Cursor getRAByRASID(int RASID) {
        Cursor res = db.rawQuery("SELECT * FROM " + T_RA + " WHERE RASID = " + RASID + " ORDER BY DID", null);
        return res;
    }

    //updateRA(RollAttack ra)
    public void updateRA(RollAttack ra)
    {
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
        Cursor res = db.rawQuery("SELECT * FROM " + T_RA + " WHERE ID = " + RAID, null);

        while (res.moveToNext()) {
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
        ContentValues cv = new ContentValues();

        cv.put(RAS_1, ras.ChapID);
        cv.put(RAS_2, ras.DSID);
        cv.put(RAS_3, ras.Initiative);
        cv.put(RAS_4, ras.RollToHit);

        db.insert(T_RAS, null, cv);
    }

    //getRAS(int RASID)
    public RollAttackSet getRAS(int RASID)
    {
        RollAttackSet ras = new RollAttackSet();
        Cursor res = db.rawQuery("SELECT * FROM " + T_RAS + " WHERE ID = " + RASID, null);

        while (res.moveToNext()) {
            ras.ID = res.getInt(0);
            ras.ChapID = res.getInt(1);
            ras.DSID = res.getInt(2);
            ras.Initiative = res.getInt(3);
            ras.RollToHit = res.getInt(4);
        }

        return ras;
    }

    //getRASetsByChapID
    public Cursor getRASetsByChapID(int chapID) {
        Cursor res = db.rawQuery("SELECT * FROM " + T_RAS + " WHERE ChapID = " + chapID, null);
        return res;
    }

    //getDSIDByRASID(int RASID)
    public int getDSIDByRASID(int RASID)
    {
        int dsid = 0;
        Cursor res = db.rawQuery("SELECT DSID FROM " + T_RAS + " WHERE ID = " + RASID, null);

        while (res.moveToNext())
            dsid = res.getInt(0);

        return dsid;
    }

    //getLatestRASID()
    public int getLatestRASID()
    {
        int r = 0;
        Cursor res = db.rawQuery("SELECT ID FROM " + T_RAS + " ORDER BY ID DESC LIMIT 1", null);

        while (res.moveToNext())
            r = res.getInt(0);

        return r;
    }

    //endregion

    //v2
    //region **DashboardSetting table calls**

    //getDSNByID
    public String getDSNByID(int DSID) {
        String dsn = "";
        Cursor res = db.rawQuery("SELECT Name FROM " + T_DASH + " WHERE ID = " + DSID, null);

        while (res.moveToNext())
            dsn = res.getString(0);

        return dsn;
    }

    //getDSID(string DSN)
    public int getDSID(String DSN) {
        int dsid = 0;
        Cursor res = db.rawQuery("SELECT ID FROM " + T_DASH + " WHERE Name = '" + DSN + "'", null);

        while (res.moveToNext())
            dsid = res.getInt(0);

        return dsid;
    }

    //getAllDS
    public Cursor getAllDash() {
        Cursor res = db.rawQuery("SELECT * FROM " + T_DASH, null);
        return res;
    }

    //endregion

    //v2
    //region **DashboardGrid table calls**

    //getAllDashboardGrids
    public Cursor getAllDashboardGrids() {
        Cursor res = db.rawQuery("SELECT * FROM " + T_DASH_GRID, null);
        return res;
    }

    //getDGByID(int ID)
    public DashboardGrid getGridByID(int ID) {
        DashboardGrid dg = new DashboardGrid();
        Cursor res = db.rawQuery("SELECT * FROM " + T_DASH_GRID + " WHERE ID = " + ID, null);

        while (res.moveToNext()) {
            dg.ID = res.getInt(0);
            dg.Position = res.getString(1);
            dg.DSID = res.getInt(2);
            dg.TileColor = res.getString(3);
            dg.TileTextColor = res.getString(4);
        }

        return dg;
    }

    //getDSByID(int DSID)
    public DashboardSetting getDSByDSID(int DSID) {
        DashboardSetting ds = new DashboardSetting();
        Cursor res = db.rawQuery("SELECT * FROM " + T_DASH + " WHERE ID = " + DSID, null);

        while (res.moveToNext()) {
            ds.ID  = res.getInt(0);
            ds.Name = res.getString(1);
            ds.Description = res.getString(2);
        }

        return ds;
    }

    //updateDG(DGID, DSID)
    public void updateDG(int DGID, int DSID, String color) {
        ContentValues cv = new ContentValues();

        cv.put("ID", DGID);
        cv.put("DSID", DSID);
        cv.put("TileColor", color);

        String strFilter = "ID=" + DGID;

        db.update(T_DASH_GRID, cv, strFilter, null);
    }

    //getTileColor(DGID)
    public String getTileColor(int DGID) {
        String color = "";
        Cursor res = db.rawQuery("SELECT TileColor FROM " + T_DASH_GRID + " WHERE ID = " + DGID, null);
        while (res.moveToNext())
            color = res.getString(0);

        return color;
    }

    //valDGOptions(int DSID)
    public boolean valDGOptions(int DSID) {
        Cursor res = db.rawQuery("SELECT * FROM " + T_DASH_GRID + " WHERE DSID = "  + DSID, null);
        if (res.getCount() == 1)
            return false;
        else
            return true;
    }

    //endregion

}