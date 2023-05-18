package com.example.myapplication.model;

import androidx.room.PrimaryKey;

import java.io.Serializable;

public class Tour implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name,startDate,trans,duration,img,total;

    public Tour() {
    }

    public Tour(String name, String startDate, String trans, String duration, String img, String total) {
        this.name = name;
        this.startDate = startDate;
        this.trans = trans;
        this.duration = duration;
        this.img = img;
        this.total = total;
    }

    public Tour(int id, String name, String startDate, String trans, String duration, String img, String total) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.trans = trans;
        this.duration = duration;
        this.img = img;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
