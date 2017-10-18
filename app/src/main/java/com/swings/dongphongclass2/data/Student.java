package com.swings.dongphongclass2.data;

import java.net.URL;

/**
 * Created by sonnguyen on 8/15/17.
 */

public class Student {
    private String id,name,phonenum;
    private int numberOfClass;
    private long beginday,bithday;
    private String avatarLink;
    private boolean fee;
    private double amount;
    private int numofFee = 0;

    public Student(String name, String phonenum, long beginday) {
        this.name = name;
        this.phonenum = phonenum;
        this.beginday = beginday;
        fee = false;
        numberOfClass = 0;

    }

    public Student() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public int getNumberOfClass() {
        return numberOfClass;
    }

    public void setNumberOfClass(int numberOfClass) {
        this.numberOfClass = numberOfClass;
    }

    public long getBeginday() {
        return beginday;
    }

    public void setBeginday(long beginday) {
        this.beginday = beginday;
    }

    public long getBithday() {
        return bithday;
    }

    public void setBithday(long bithday) {
        this.bithday = bithday;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public boolean isFee() {
        return fee;
    }

    public void setFee(boolean fee) {
        this.fee = fee;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getNumofFee() {
        return numofFee;
    }

    public void setNumofFee(int numofFee) {
        this.numofFee = numofFee;
    }
}
