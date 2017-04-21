package com.hellwebstudios.zweber.dd.DataObjects;

/**
 * Created by zweber on 1/1/2017.
 */
public class CharClass {

    public int ClassID;
    public String ClassName;

    public CharClass() { }
    public CharClass(int classID, String className) {
        ClassID = classID;
        ClassName = className;
    }

    public int getClassID() { return ClassID; }
    public String getClassName() { return ClassName; }
}
