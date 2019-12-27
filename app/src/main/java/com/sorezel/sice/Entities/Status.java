package com.sorezel.sice.Entities;

import java.io.Serializable;

public class Status implements Serializable {

    private short ID;
    private String desc;

    public Status() {
    }

    public Status(short ID, String desc) {
        this.ID = ID;
        this.desc = desc;
    }

    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
