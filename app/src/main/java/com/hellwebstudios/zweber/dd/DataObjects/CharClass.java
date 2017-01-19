package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/1/2017.
 */
public class CharClass {

    public int ClassID;
    public String ClassName;

    //Empty Constructor
    public CharClass()
    {

    }

    //Loaded constructor
    public CharClass(int classID, String className) {
        ClassID = classID;
        ClassName = className;
    }

    public int getClassID() {
        return ClassID;
    }

    public void setClassID(int classID) {
        ClassID = classID;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }
}
