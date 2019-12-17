package com.sorezel.sice.Entities;

import java.io.Serializable;

public class Status implements Serializable {

    char ID;
    String desc;

    public Status() {
    }

    public Status(char ID, String desc) {
        this.ID = ID;
        this.desc = desc;
    }

    public char getID() {
        return ID;
    }

    public void setID(char ID) {
        this.ID = ID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
