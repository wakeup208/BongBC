package com.example.myapplication.model;

import androidx.room.PrimaryKey;

import com.example.myapplication.db.UserSQL;
import java.io.Serializable;

public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name,birthday,gender,image,role;
    private int accountId;
    public User() {
    }

    public User(String name, String birthday, String gender, String image, String role) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.image = image;
        this.role = role;
    }

    public User(int id, String name, String birthday, String gender, String image, String role, int accountId) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.image = image;
        this.role = role;
        this.accountId = accountId;
    }

    public User(int id, String name, String birthday, String gender, String image, String role) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.image = image;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public User(String name, String birthday, String gender, String image, String role, int accountId) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.image = image;
        this.role = role;
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", image=" + image +
                ", role='" + role + '\'' +
                '}';
    }
}
