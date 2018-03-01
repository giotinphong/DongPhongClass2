package com.swings.dongphongclass2.data;

import java.util.ArrayList;

/**
 * Created by sonnguyen on 10/25/17.
 */

public class Group {
    private String groupName,id;
    private ArrayList<String> groupMem;

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group(String groupName, ArrayList<String> groupMem) {

        this.groupName = groupName;
        this.groupMem = groupMem;
    }

    public Group() {

    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<String> getGroupMem() {
        return groupMem;
    }

    public void setGroupMem(ArrayList<String> groupMem) {
        this.groupMem = groupMem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
