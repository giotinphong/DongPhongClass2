package com.swings.dongphongclass2.data;

/**
 * Created by sonnguyen on 3/2/18.
 */

public class StudentSchedule  {
    private String idSchedule,note;
    private long date;
    private boolean isPresent;

    public StudentSchedule(long date, boolean isPresent) {
        this.date = date;
        this.isPresent = isPresent;
        this.idSchedule = "";
        this.note = "";
    }

    public StudentSchedule(String note, long date, boolean isPresent) {
        this.idSchedule = "";
        this.note = note;
        this.date = date;
        this.isPresent = isPresent;
    }

    public String getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }

    public String getNote() {
        return note;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public StudentSchedule() {

    }

    public StudentSchedule(String note, long date) {

        this.note = note;
        this.date = date;
    }

    public StudentSchedule(long date) {

        this.date = date;
    }
}
