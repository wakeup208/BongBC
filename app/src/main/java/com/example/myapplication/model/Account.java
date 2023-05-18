package com.example.myapplication.model;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username,password;
    private List<User> listUser;
    public Account(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(int id, String username) {
        this.id = id;
        this.username = username;
    }


    public Account() {
    }

    public List<User> getListUser() {
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", listUser=" + listUser +
                '}';
    }

}
