package com.swings.dongphongclass2.data;

import java.util.ArrayList;

/**
 * Created by sonnguyen on 3/16/18.
 */

public class StudentClass {
    private String IdClass;
    private String ClassName;
    private ArrayList<String> StudentArray;

    public StudentClass(String className, ArrayList<String> studentArray) {
        ClassName = className;
        StudentArray = studentArray;
    }

    public StudentClass() {
    }

    public String getIdClass() {
        return IdClass;
    }

    public void setIdClass(String idClass) {
        IdClass = idClass;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public ArrayList<String> getStudentArray() {
        return StudentArray;
    }

    public void setStudentArray(ArrayList<String> studentArray) {
        StudentArray = studentArray;
    }
}
